package com.kodlamaio.filterService.business.responses.get;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetAllFiltersResponse {
	private String id;

	private String carId;

	private String brandId;

	private String modelId;

	private String brandName;

	private String modelName;

	private String plate;

	private double dailyPrice;

	private int modelYear;

	private int state;
}
