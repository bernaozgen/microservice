package com.kodlamaio.paymentservice.business.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreatePaymentResponse {
	private String id;
	private String cardNo;
	private String cardHolder;
	private String cvv;
	private String cardDate;
	private double balance;

}
