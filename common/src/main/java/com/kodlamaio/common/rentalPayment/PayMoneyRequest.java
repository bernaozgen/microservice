package com.kodlamaio.common.rentalPayment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PayMoneyRequest {
	private String RentalId;
	private String cardNo;
	private String cardHolder;
	private double balance;
	private double totalPrice;

}

