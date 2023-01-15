package com.kodlamaio.common.events.inventory.car;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarCreatedEvent {
	private String message;
	private String carId;
	private String brandId;
	private String modelId;
	private String brandName;
	private String modelName;
	
}
