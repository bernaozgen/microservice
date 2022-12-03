package com.kodlamaio.paymentservice.dataAccess;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kodlamaio.paymentservice.entities.Payment;

public interface PaymentRepository extends JpaRepository<Payment, String>{

}
