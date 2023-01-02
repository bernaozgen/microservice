package com.kodlamaio.paymentservice.business.concretes;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.kodlamaio.common.events.payments.PaymentCreatedEvent;
import com.kodlamaio.common.utilities.exceptions.BusinessException;
import com.kodlamaio.common.utilities.mapping.ModelMapperService;
import com.kodlamaio.common.utilities.results.DataResult;
import com.kodlamaio.common.utilities.results.Result;
import com.kodlamaio.common.utilities.results.SuccessDataResult;
import com.kodlamaio.common.utilities.results.SuccessResult;
import com.kodlamaio.paymentservice.adapters.PosCheckService;
import com.kodlamaio.paymentservice.business.abstracts.PaymentService;
import com.kodlamaio.paymentservice.business.constants.Messages;
import com.kodlamaio.paymentservice.business.requests.CreatePaymentRequest;
import com.kodlamaio.paymentservice.business.responses.CreatePaymentResponse;
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
	public DataResult<CreatePaymentResponse> add(CreatePaymentRequest createPaymentRequest) {
//		checkIfBalanceUnderTotalPrice(createPaymentRequest.getTotalPrice(), createPaymentRequest.getBalance());
		Payment payment = this.modelMapperService.forRequest().map(createPaymentRequest, Payment.class);
		payment.setId(UUID.randomUUID().toString());
//		posCheckService.pay();
		this.paymentRepository.save(payment);
		
		PaymentCreatedEvent paymentCreatedEvent = new PaymentCreatedEvent();
		paymentCreatedEvent.setRentalId(createPaymentRequest.getRentalId());
		paymentCreatedEvent.setMessage("Payment Created");

		this.paymentProducer.sendMessage(paymentCreatedEvent);
		CreatePaymentResponse createPaymentResponse=this.modelMapperService.forResponse().map(payment, CreatePaymentResponse.class); 
		
		return new SuccessDataResult<CreatePaymentResponse>(createPaymentResponse, Messages.PaymentCreated);

	}

	@Override
	public void updateStatus(String id, int status) {
		Payment payment = this.paymentRepository.findById(id).get();
		payment.setStatus(status);
		paymentRepository.save(payment);
		
	}

	@Override
	public Result delete(String id) {
		paymentRepository.deleteById(id);
		return new SuccessResult(Messages.PaymentDeleted);
	}

	private void checkIfBalanceUnderTotalPrice(double totalPrice, double balance) {
		if (balance < totalPrice) {
			throw new BusinessException("insufficient balance");
		}

	}

}
