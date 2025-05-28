package com.order.payload;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponseDTO {
  private UUID id;
  private String userId;
  private String status;
  private String itemName;
  private String itemId;
  private int quantity;
  private BigDecimal unitPrice;
  private BigDecimal totalAmount;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
}
