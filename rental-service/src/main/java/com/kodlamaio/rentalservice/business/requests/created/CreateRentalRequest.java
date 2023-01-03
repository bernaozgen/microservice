package com.kodlamaio.rentalservice.business.requests.created;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateRentalRequest {
	@NotNull
	@NotEmpty
	private String carId;
	@NotNull
	private int rentedForDays;
	@NotNull
	private double dailyPrice;
	@NotNull
	private int condition;

}
