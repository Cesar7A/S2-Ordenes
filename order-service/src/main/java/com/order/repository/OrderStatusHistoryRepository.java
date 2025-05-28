package com.order.repository;

import com.order.model.OrderStatusHistory;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderStatusHistoryRepository extends JpaRepository<OrderStatusHistory, UUID> {
  List<OrderStatusHistory> findByOrderId(UUID orderId);
}
