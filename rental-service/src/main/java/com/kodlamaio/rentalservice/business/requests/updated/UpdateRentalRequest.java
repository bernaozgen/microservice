package com.kodlamaio.rentalservice.business.requests.updated;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateRentalRequest {
	@NotNull 
	@NotEmpty
	private String id;
    @NotEmpty
    @NotNull
	private String carId;
    @NotNull
	private int rentedForDays;
    @NotNull
	private double dailyPrice;


}
