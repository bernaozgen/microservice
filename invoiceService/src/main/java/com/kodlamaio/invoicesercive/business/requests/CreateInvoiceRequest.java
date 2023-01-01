package com.kodlamaio.invoicesercive.business.requests;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateInvoiceRequest {
	@NotNull
	@NotEmpty
	private String paymentId;
	@NotEmpty
	private String customerFirstName;
	@NotEmpty
	private String customerLastName;
	@NotNull
	private double tax;
	@NotNull
	private double totalPrice;
	@NotEmpty
	private String adress;
}
