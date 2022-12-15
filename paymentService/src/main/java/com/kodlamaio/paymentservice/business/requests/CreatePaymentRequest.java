package com.kodlamaio.paymentservice.business.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreatePaymentRequest {
	private String rentalId;
	private double totalPrice;
	private double balance;
	private String cardHolder;
	private String cardNo;
	

}
