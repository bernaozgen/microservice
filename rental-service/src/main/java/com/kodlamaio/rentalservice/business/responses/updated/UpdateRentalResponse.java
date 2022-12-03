package com.kodlamaio.rentalservice.business.responses.updated;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateRentalResponse {
	private String id;
	private String carId;
	private int rentedForDays;
	private double dailyPrice;
	private String state;
}
