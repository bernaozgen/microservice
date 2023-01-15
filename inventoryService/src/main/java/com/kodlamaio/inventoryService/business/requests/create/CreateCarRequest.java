package com.kodlamaio.inventoryService.business.requests.create;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateCarRequest {
	@NotNull
	@NotBlank
	private String modelId;
	@NotNull
	@NotBlank
	private String plate;	
	@Min(2015)
	private int modelYear;
	@Min(1)
	@Max(2)
	private int state;
	@Min(10)
	private double dailyPrice;



}
