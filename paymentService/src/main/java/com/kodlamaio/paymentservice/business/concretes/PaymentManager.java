package com.kodlamaio.paymentservice.business.concretes;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.kodlamaio.common.events.payments.PaymentCreatedEvent;
import com.kodlamaio.common.utilities.exceptions.BusinessException;
import com.kodlamaio.common.utilities.mapping.ModelMapperService;
import com.kodlamaio.paymentservice.adapters.PosCheckService;
import com.kodlamaio.paymentservice.business.abstracts.PaymentService;
import com.kodlamaio.paymentservice.business.requests.CreatePaymentRequest;
import com.kodlamaio.paymentservice.dataAccess.PaymentRepository;
import com.kodlamaio.paymentservice.entities.Payment;
import com.kodlamaio.paymentservice.kafka.PaymentProducer;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PaymentManager implements PaymentService {
	private PaymentRepository paymentRepository;
	private ModelMapperService modelMapperService;
	private PaymentProducer paymentProducer;
	private PosCheckService posCheckService;

	@Override
	public void add(CreatePaymentRequest createPaymentRequest) {

		Payment payment = this.modelMapperService.forRequest().map(createPaymentRequest, Payment.class);
		checkIfBalanceUnderTotalPrice(createPaymentRequest.getTotalPrice(), createPaymentRequest.getBalance());
		payment.setId(UUID.randomUUID().toString());
		posCheckService.pay();
		this.paymentRepository.save(payment);
		PaymentCreatedEvent paymentCreatedEvent = new PaymentCreatedEvent();
		paymentCreatedEvent.setRentalId(createPaymentRequest.getRentalId());
		paymentCreatedEvent.setMessage("Payment Created");

		this.paymentProducer.sendMessage(paymentCreatedEvent);

	}

	@Override
	public void updateStatus(String id, int status) {
		Payment payment = this.paymentRepository.findById(id).get();
		payment.setStatus(status);
		paymentRepository.save(payment);
	}

	@Override
	public void delete(String id) {
		paymentRepository.deleteById(id);
	}

	private void checkIfBalanceUnderTotalPrice(double totalPrice, double balance) {
		if (balance < totalPrice) {
			throw new BusinessException("insufficient balance");
		}

	}

}
