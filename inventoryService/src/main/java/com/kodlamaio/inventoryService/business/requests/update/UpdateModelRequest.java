package com.kodlamaio.inventoryService.business.requests.update;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateModelRequest {
	@NotNull
	@NotEmpty
	private String id;
	@NotNull
	@NotEmpty
	private String brandId;
	@NotNull
	@NotEmpty
	private String name;

}
