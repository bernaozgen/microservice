package com.kodlamaio.paymentservice.business.concretes;

import java.util.UUID;

import org.springframework.stereotype.Service;

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

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PaymentManager implements PaymentService {
	private PaymentRepository paymentRepository;
	private ModelMapperService modelMapperService;
	private PosCheckService posCheckService;

	@Override
	public DataResult<CreatePaymentResponse> add(CreatePaymentRequest createPaymentRequest) {
		// sisteme card bilgilerini kaydediyoruz
		Payment payment = this.modelMapperService.forRequest().map(createPaymentRequest, Payment.class);
		payment.setId(UUID.randomUUID().toString());
		this.paymentRepository.save(payment);
		CreatePaymentResponse createPaymentResponse = this.modelMapperService.forResponse().map(payment,
				CreatePaymentResponse.class);
		return new SuccessDataResult<CreatePaymentResponse>(createPaymentResponse, Messages.PaymentCreated);
	}

	private void checkIfRentalBalance(String cardNo, String cardHolder, String cvv, double totalPrice) {
		Payment payment = this.paymentRepository.findByCardNoAndCardHolderAndCvv(cardNo, cardHolder, cvv);
		if (payment == null) {
			throw new BusinessException(Messages.InvalidPayment);
		}
		double amount = this.paymentRepository.findByCardNo(cardNo).getBalance();
		if (amount < totalPrice) {
			throw new BusinessException(Messages.InsufficientBalance);
		}
		posCheckService.pay();
		Payment payment2 = this.paymentRepository.findByCardNo(cardNo);
		payment2.setBalance(amount - totalPrice);
		this.paymentRepository.save(payment2);
	}

	@Override
	public void paymentReceived(String cardNo, String cardHolder, String cvv, double balance) {
		checkIfRentalBalance(cardNo, cardHolder, cvv, balance);// card bilgisi kontrol ve ödeme işlemi

	}

	@Override
	public Result delete(String id) {
		paymentRepository.deleteById(id);
		return new SuccessResult(Messages.PaymentDeleted);
	}

}
