package com.kodlamaio.invoiceservice.business.requests;

import java.time.LocalDate;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateInvoiceRequest {
	@NotEmpty
	@NotNull
	private String carId;
	@NotEmpty
	@NotNull
	private String fullName;
	@NotEmpty
	@NotNull
	private String modelName;
	@NotEmpty
	@NotNull
	private String brandName;
	@NotNull
	private int modelYear;
	@NotNull
	private double dailyPrice;
	@NotNull
	private double totalPrice;
	@NotNull
	private int rentedForDays;
	private LocalDate rentedDate;
}
