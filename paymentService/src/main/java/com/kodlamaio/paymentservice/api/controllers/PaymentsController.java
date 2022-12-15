package com.kodlamaio.paymentservice.api.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kodlamaio.paymentservice.business.abstracts.PaymentService;
import com.kodlamaio.paymentservice.business.requests.CreatePaymentRequest;

import lombok.AllArgsConstructor;

@RequestMapping("/api/payments")
@RestController
@AllArgsConstructor
public class PaymentsController {
	private PaymentService paymentService;

	@PostMapping
	public void add(CreatePaymentRequest createPaymentRequest) {
		this.paymentService.add(createPaymentRequest);
	}

}
