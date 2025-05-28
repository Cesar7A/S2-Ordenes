package com.order.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.order.model.Order;
import com.order.model.OrderStatus;
import com.order.model.OrderStatusHistory;
import com.order.payload.OrderRequestDTO;
import com.order.payload.OrderResponseDTO;
import com.order.repository.OrderRepository;
import com.order.repository.OrderStatusHistoryRepository;

import jakarta.transaction.Transactional;

@Service
public class OrderService {

  @Autowired
  private OrderRepository orderRepository;

  @Autowired
  private OrderStatusHistoryRepository historyRepository;

  @Autowired
  private EmailService emailService;

  @Value("${spring.mail.username}")
  private String fromEmail;




  public OrderResponseDTO createOrder(String userId, OrderRequestDTO dto) {
  Order order = new Order();
  order.setUserId(userId);
  order.setItemName(dto.getItemName());
  order.setItemId(dto.getItemId());
  order.setQuantity(dto.getQuantity());
  order.setUnitPrice(dto.getUnitPrice());

  // Calcular totalAmount
  BigDecimal totalAmount = dto.getUnitPrice().multiply(BigDecimal.valueOf(dto.getQuantity()));
  order.setTotalAmount(totalAmount);

  order.setOrderStatus(OrderStatus.PENDING);
  order.setCreatedAt(LocalDateTime.now());
  order.setUpdatedAt(LocalDateTime.now());

  emailService.sendOrderStatusUpdateEmail(
      "a@gmail.com",
      order.getUserId(),
      "Order Received",
      order.getOrderStatus().name(),
      order.getTotalAmount()
  );

  return toDto(orderRepository.save(order));
 }

  public Optional<OrderResponseDTO> getOrder(UUID id) {
    return orderRepository.findById(id)
        .map(this::toDto);
  }

  public List<OrderResponseDTO> getUsersAllOrders() {
    return orderRepository.findAll().stream()
        .map(this::toDto)
        .collect(Collectors.toList());
  }

  public List<OrderResponseDTO> getsAllOrders() {
    return orderRepository.findAll().stream()
        .map(this::toDto)
        .collect(Collectors.toList());
  }

  @Transactional
  public OrderResponseDTO updateOrderStatus(UUID id, OrderStatus newStatus) {
    Order order = orderRepository.findById(id)
        .orElseThrow();

    OrderStatus oldStatus = order.getOrderStatus();
    order.setOrderStatus(newStatus);
    order.setUpdatedAt(java.time.LocalDateTime.now());

    orderRepository.save(order);

    OrderStatusHistory history = new OrderStatusHistory();
    history.setOrder(order);
    history.setOldStatus(oldStatus);
    history.setNewStatus(newStatus);
    historyRepository.save(history);
    //replace with the  user email
    emailService.sendOrderStatusUpdateEmail("a@gmail.com",order.getUserId(), oldStatus.name(), order.getOrderStatus().name(),order.getTotalAmount());

    return toDto(order);
  }

  public List<OrderResponseDTO> getOrderHistory(String userId) {
    return orderRepository.findByUserId(userId).stream().map(this::toDto)
        .collect(Collectors.toList());
  }

  public OrderResponseDTO cancelOrder(UUID id) {
    Order order = orderRepository.findById(id)
        .orElseThrow();
    //replace with user email
    emailService.sendOrderStatusUpdateEmail("a@gmail.com",order.getUserId(),"", order.getOrderStatus().name(),order.getTotalAmount());
    return updateOrderStatus(id, OrderStatus.CANCELED);
  }

  private OrderResponseDTO toDto(Order order) {
  return new OrderResponseDTO(
      order.getId(),
      order.getUserId(),
      order.getOrderStatus().name(),
      order.getItemName(),
      order.getItemId(),
      order.getQuantity(),
      order.getUnitPrice(),
      order.getTotalAmount(),
      order.getCreatedAt(),
      order.getUpdatedAt()
  );
  }
}
