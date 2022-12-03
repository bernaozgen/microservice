package com.kodlamaio.rentalservice.business.requests.created;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateRentalRequest {

	private String carId;
	private int rentedForDays;
	private double dailyPrice;

}
