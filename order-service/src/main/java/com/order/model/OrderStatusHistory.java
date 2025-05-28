package com.order.model;


import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderStatusHistory {

  @Id
  @GeneratedValue
  private UUID id;

  @ManyToOne
  private Order order;

  @Enumerated(EnumType.STRING)
  private OrderStatus oldStatus;

  @Enumerated(EnumType.STRING)
  private OrderStatus newStatus;

  private LocalDateTime changedAt = LocalDateTime.now();

}