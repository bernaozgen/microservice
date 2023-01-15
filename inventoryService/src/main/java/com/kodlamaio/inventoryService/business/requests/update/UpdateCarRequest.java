package com.kodlamaio.inventoryService.business.requests.update;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCarRequest {
	@NotNull
	@NotEmpty
	private String id;
	@NotEmpty
	@NotNull
	private String modelId;
	@NotEmpty
	@NotNull
	private String plate;
	@Min(1)
	@Max(2)
	private int state;	
	@Min(2015)
	private int modelYear;
	@Min(10)
	private double dailyPrice;

}
