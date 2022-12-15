package com.kodlamaio.common.rentalPayment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PayMoneyRequest {
	private String cardHolder;
	private String cardNo;
	private String RentalId;
	private double totalPrice;
	private double balance;
}

