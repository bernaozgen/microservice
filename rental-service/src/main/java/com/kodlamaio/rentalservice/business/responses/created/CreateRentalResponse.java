package com.kodlamaio.rentalservice.business.responses.created;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateRentalResponse {

	private String id;
	private String carId;
	private LocalDateTime dateStartedId;
	private int rentedForDays;
	private double dailyPrice;
	private double totalPrice;
}
