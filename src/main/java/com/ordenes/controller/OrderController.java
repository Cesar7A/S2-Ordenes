package com.ordenes.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ordenes.model.Order;
import com.ordenes.repository.OrderRepository;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderRepository orderRepository;

    public OrderController(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @GetMapping
    public List<Order> getAll() {
        return orderRepository.findAll();
    }

    @PostMapping
    public Order create(@RequestBody Order order) {
        return orderRepository.save(order);
    }

    @GetMapping("/{id}")
    public Order getById(@PathVariable UUID id) {
        return orderRepository.findById(id).orElseThrow();
    }

    @PutMapping("/{id}")
    public Order update(@PathVariable UUID id, @RequestBody Order orderDetails) {
        Order order = orderRepository.findById(id).orElseThrow();
        order.setStatus(orderDetails.getStatus());
        order.setShippingAddress(orderDetails.getShippingAddress());
        order.setTotal(orderDetails.getTotal());
        return orderRepository.save(order);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        orderRepository.deleteById(id);
    }
}
