package com.kodlamaio.paymentservice.api.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kodlamaio.common.utilities.results.DataResult;
import com.kodlamaio.common.utilities.results.Result;
import com.kodlamaio.paymentservice.business.abstracts.PaymentService;
import com.kodlamaio.paymentservice.business.requests.CreatePaymentRequest;
import com.kodlamaio.paymentservice.business.responses.CreatePaymentResponse;

import lombok.AllArgsConstructor;

@RequestMapping("/api/payments")
@RestController
@AllArgsConstructor
public class PaymentsController {
	private PaymentService paymentService;

	@PostMapping
	public ResponseEntity<?> add(CreatePaymentRequest createPaymentRequest) {
		DataResult<CreatePaymentResponse> result = this.paymentService.add(createPaymentRequest);
		if (result.isSuccess()) {
			return ResponseEntity.ok(result);
		}
		return ResponseEntity.badRequest().body(result);
	}

	@PostMapping("/received")
	public void paymentReceived(@RequestParam String cardNo, @RequestParam String cardHolder, @RequestParam String cvv,
			@RequestParam double totalPrice) {

		paymentService.paymentReceived(cardNo, cardHolder, cvv, totalPrice);

	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(String id) {
		Result result = this.paymentService.delete(id);
		if (result.isSuccess()) {
			return ResponseEntity.ok(result);
		}
		return ResponseEntity.badRequest().body(result);
	}

}
