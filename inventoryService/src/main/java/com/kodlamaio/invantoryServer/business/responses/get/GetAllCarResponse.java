package com.kodlamaio.invantoryServer.business.responses.get;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetAllCarResponse {
	private String id;
	private String carId;
	private double dailyPrice;
	private int modelYear;
	private String plate;
	private int state;
	private String brandName;
	private String modelName;
}
