package com.order.controller;

import com.order.model.OrderStatus;
import com.order.payload.OrderRequestDTO;
import com.order.payload.OrderResponseDTO;
import com.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/orders")
//@RequestMapping("/users/{userId}/orders")
@CrossOrigin("*")
public class OrderController {

  @Autowired
  private OrderService orderService;

  @PostMapping("/user/{userId}")
  public OrderResponseDTO createOrder(@PathVariable String userId, @RequestBody OrderRequestDTO request) {
    return orderService.createOrder(userId, request);
  }

  @GetMapping("/{id}")
  public OrderResponseDTO getOrder(@PathVariable UUID id) {
    return orderService.getOrder(id).orElseThrow();
  }

  @GetMapping
  public List<OrderResponseDTO> getAllOrders() {
    return orderService.getUsersAllOrders();
  }

  @PostMapping("/{id}/status")
  public OrderResponseDTO updateOrderStatus(
      @PathVariable UUID id,
      @RequestBody Map<String, String> request
  ) {
    OrderStatus newStatus = OrderStatus.valueOf(request.get("newStatus"));
    return orderService.updateOrderStatus(id, newStatus);
  }
  @GetMapping("/user/{userId}/history")
  public List<OrderResponseDTO> getOrderHistory(@PathVariable String  userId) {
    return orderService.getOrderHistory(userId);
  }

  @DeleteMapping("/{id}")
  public OrderResponseDTO cancelOrder(@PathVariable UUID id) {
    return orderService.cancelOrder(id);
  }

  @GetMapping("/internal/{id}")
  public OrderResponseDTO getOrderInternally(@PathVariable UUID id) {
    return orderService.getOrder(id).orElseThrow();
  }

  @PatchMapping("/internal/{id}/status")
  public OrderResponseDTO patchOrderStatusInternal(
      @PathVariable UUID id,
      @RequestBody Map<String, String> request
  ) {
    OrderStatus newStatus = OrderStatus.valueOf(request.get("newStatus"));
    return orderService.updateOrderStatus(id, newStatus);
  }

}

