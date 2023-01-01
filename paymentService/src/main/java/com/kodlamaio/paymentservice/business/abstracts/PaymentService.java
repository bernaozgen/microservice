package com.kodlamaio.paymentservice.business.abstracts;

import com.kodlamaio.paymentservice.business.requests.CreatePaymentRequest;

public interface PaymentService {
	void add(CreatePaymentRequest createPaymentRequest);

	void updateStatus(String id, int status);

	void delete(String id); 

}
