package com.kodlamaio.paymentservice.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "creditCards")
public class CreditCard{
	@Id
	@Column(name = "id")
	private int id;

	@Column(name = "card_no")
	private String cardNo;

	@Column(name = "card_holder")
	private String cardHolder;
	@Column(name="cvv")
	private int cvv;

	@Column(name = "balance")
	private double balance;// kart bakiyesi
	
	@Column(name="payment_state")
	private int paymentState;
}
