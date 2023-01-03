package com.kodlamaio.invoicesercive.business.requests;

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
	private String carId;

	private String fullName;

	private String modelName;

	private String brandName;

	private int modelYear;

	private double dailyPrice;

	private double totalPrice;

	private int rentedForDays;

	private LocalDate rentedDate;
}
