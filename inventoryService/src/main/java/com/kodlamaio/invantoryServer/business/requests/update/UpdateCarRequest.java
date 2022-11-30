package com.kodlamaio.invantoryServer.business.requests.update;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCarRequest {
	private String id;
	private double dailyPrice;
	private int modelYear;
	private String plate;
	private int state;
	private String modelId;

}
