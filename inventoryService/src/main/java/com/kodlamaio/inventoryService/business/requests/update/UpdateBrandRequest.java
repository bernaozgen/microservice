package com.kodlamaio.inventoryService.business.requests.update;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateBrandRequest {
	@NotEmpty
	@NotNull
	private String id;
	@NotEmpty
	@NotNull
	private String name;

}
