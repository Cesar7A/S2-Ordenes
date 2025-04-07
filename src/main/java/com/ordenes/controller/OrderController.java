package com.ordenes.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ordenes.model.Order;
import com.ordenes.model.OrderStatus;
import com.ordenes.service.OrderService;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public List<Order> getAll() {
        return orderService.getAllOrders();
    }

    @PostMapping
    public Order create(@RequestBody Order order) {
        return orderService.createOrderWithPayment(order);
    }

    @GetMapping("/{id}")
    public Order getById(@PathVariable UUID id) {
        return orderService.getOrderById(id);
    }

    @PutMapping("/{id}")
    public Order update(@PathVariable UUID id, @RequestBody Order orderDetails) {
        return orderService.updateOrder(id, orderDetails);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        orderService.deleteOrder(id);
    }

    @PutMapping("/{id}/estado")
    public ResponseEntity<Order> actualizarEstado(
            @PathVariable UUID id,
            @RequestParam("estado") String estado) {
        OrderStatus nuevoEstado = OrderStatus.valueOf(estado.toUpperCase());
        Order actualizada = orderService.actualizarEstadoYNotificar(id, nuevoEstado);
        return ResponseEntity.ok(actualizada);
    }
}
