package com.order.payload;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequestDTO {
  private String itemName;
  private String itemId;
  private int quantity;
  private BigDecimal unitPrice;
}
