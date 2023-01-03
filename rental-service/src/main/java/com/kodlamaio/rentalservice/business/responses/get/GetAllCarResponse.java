package com.kodlamaio.rentalservice.business.responses.get;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetAllCarResponse {
	private String id;
	private double dailyPrice;
	private int modelYear;
	private String plate;
	private int state;
	private String brandName;
	private String modelName;
}
