package com.kodlamaio.paymentservice.business.requests;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreatePaymentRequest {
	@NotEmpty
	@NotNull
	@Max(16)
	private String cardNo;
	@NotEmpty
	@NotNull
	private String cardHolder;
	@NotEmpty
	@NotNull
	private String cvv;
	@NotEmpty
	@NotNull
	private String cardDate;
	@NotNull
	private double balance;
}
