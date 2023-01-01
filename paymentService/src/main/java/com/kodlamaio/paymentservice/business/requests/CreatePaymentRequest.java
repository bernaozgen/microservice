package com.kodlamaio.paymentservice.business.requests;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreatePaymentRequest {
	@NotNull
	@NotEmpty
	private String rentalId;
	@NotNull
	private double totalPrice;
	@NotNull
	private double balance;
	@NotNull
	@NotEmpty
	private String cardHolder;
	@NotNull
	@NotEmpty
	private String cardNo;
	

}
