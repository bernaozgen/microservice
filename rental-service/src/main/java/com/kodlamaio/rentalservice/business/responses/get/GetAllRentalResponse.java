package com.kodlamaio.rentalservice.business.responses.get;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetAllRentalResponse {

	private String id;
	private String carId;
	private LocalDateTime dateStartedId;
	private int rentedForDays;
	private double dailyPrice;
	private double totalPrice;
	
}
