package com.kodlamaio.inventoryService.business.requests.create;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateModelRequest {
	@NotBlank
	@NotNull
	private String brandId;
	@NotBlank
	@NotNull
	@Size(min = 2, max = 20)
	private String name;

}
