package com.kodlamaio.invoicesercive.business.requests;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateInvoiceRequest {
    @NotNull
    @NotEmpty
	private String id;
    @NotNull
    @NotEmpty
	private String paymentId;
    @NotNull
    @NotEmpty
	private String customerFirstName;
    @NotNull
    @NotEmpty
	private String customerLastName;
    @NotNull
	private double tax;
    @NotNull
	private double totalPrice;
    @NotNull
    @NotEmpty
	private String adress;
}
