package com.kodlamaio.inventoryService.entites;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "cars")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Car {
	@Id
	@Column(name = "id")
	private String id;
	@Column(name = "plate")
	private String plate;
	@Column(name = "modelYear")
	private int modelYear;
	@Column(name = "state")
	private int state;
	@Column(name = "dailyPrice")
	private double dailyPrice;


	@ManyToOne
	@JoinColumn(name = "model_id")
	private Model model;
	

}
