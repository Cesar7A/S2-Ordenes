package com.ordenes.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ordenes.model.Payment;

public interface PaymentRepository extends JpaRepository<Payment, UUID> {
}
