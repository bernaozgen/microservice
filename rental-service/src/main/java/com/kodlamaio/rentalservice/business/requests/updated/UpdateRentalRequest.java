package com.kodlamaio.rentalservice.business.requests.updated;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateRentalRequest {
	private String carId;
	private int rentedForDays;
	private double dailyPrice;

}
