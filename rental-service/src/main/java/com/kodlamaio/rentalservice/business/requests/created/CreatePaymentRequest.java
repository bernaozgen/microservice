package com.kodlamaio.rentalservice.business.requests.created;

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
	private String cardNo;
	@NotEmpty
	@NotNull
	private String cardHolder;
	@NotEmpty
	@NotNull
	@Max(4)
	private String cvv;
	@NotEmpty
	@NotNull
	@Max(16)
	private String cardDate;
	@NotNull
	private double balance;
}
