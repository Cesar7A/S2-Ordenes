package com.ordenes.service;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ordenes.model.Order;
import com.ordenes.model.OrderStatus;
import com.ordenes.model.Payment;
import com.ordenes.repository.OrderRepository;
import com.ordenes.repository.PaymentRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final PaymentRepository paymentRepository;
    private final EmailService emailService;
    private final PaymentGatewaySimulator paymentSimulator;

    @PersistenceContext
    private EntityManager entityManager;

    public OrderService(OrderRepository orderRepository,
                        PaymentRepository paymentRepository,
                        EmailService emailService,
                        PaymentGatewaySimulator paymentSimulator) {
        this.orderRepository = orderRepository;
        this.paymentRepository = paymentRepository;
        this.emailService = emailService;
        this.paymentSimulator = paymentSimulator;
    }

    @Transactional
    public Order createOrderWithPayment(Order order) {
        Order savedOrder = orderRepository.saveAndFlush(order);
        entityManager.flush();

        // Simula el pago de forma asincrónica
        processPaymentAsync(savedOrder);

        return savedOrder;
    }

    @Async
    public void processPaymentAsync(Order order) {
        try {
            boolean success = paymentSimulator.simulate();

            Payment payment = new Payment();
            payment.setOrder(order);
            payment.setAmount(order.getTotal());
            payment.setMethod("tarjeta");

            if (success) {
                payment.setStatus(OrderStatus.EXITOSO.name());
                paymentRepository.save(payment);

                emailService.enviarConfirmacionOrden(
                    order.getCustomerEmail(),
                    "Confirmación de tu orden",
                    "Tu orden ha sido registrada exitosamente con ID: " + order.getOrderId()
                );
            } else {
                payment.setStatus(OrderStatus.INCOMPLETO.name());
                paymentRepository.save(payment);

                emailService.enviarConfirmacionOrden(
                    order.getCustomerEmail(),
                    "Problemas con tu orden",
                    "Tu orden con ID " + order.getOrderId() + " no fue completada exitosamente. Por favor, intenta nuevamente."
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Order getOrderById(UUID id) {
        return orderRepository.findById(id).orElseThrow();
    }

    public java.util.List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Order updateOrder(UUID id, Order orderDetails) {
        Order order = orderRepository.findById(id).orElseThrow();
        order.setStatus(orderDetails.getStatus());
        order.setShippingAddress(orderDetails.getShippingAddress());
        order.setTotal(orderDetails.getTotal());
        return orderRepository.save(order);
    }

    public void deleteOrder(UUID id) {
        orderRepository.deleteById(id);
    }

    public Order actualizarEstadoYNotificar(UUID orderId, OrderStatus nuevoEstado) {
        Order order = orderRepository.findById(orderId).orElseThrow();
        order.setStatus(nuevoEstado);
        Order updatedOrder = orderRepository.save(order);

        String mensaje = switch (nuevoEstado) {
            case ENVIADO -> "Tu pedido ha sido despachado 🚚";
            case ENTREGADO -> "Tu pedido ha sido entregado ✅";
            case CANCELADO -> "Tu pedido ha sido cancelado ❌";
            default -> null;
        };

        if (mensaje != null && order.getCustomerEmail() != null) {
            emailService.enviarConfirmacionOrden(
                order.getCustomerEmail(),
                "Actualización de tu orden",
                mensaje + "\nID de orden: " + order.getOrderId()
            );
        }

        return updatedOrder;
    }
}
