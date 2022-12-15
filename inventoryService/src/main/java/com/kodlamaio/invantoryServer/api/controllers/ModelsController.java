package com.kodlamaio.invantoryServer.api.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kodlamaio.invantoryServer.business.abstracts.ModelService;
import com.kodlamaio.invantoryServer.business.requests.create.CreateModelRequest;
import com.kodlamaio.invantoryServer.business.requests.update.UpdateModelRequest;
import com.kodlamaio.invantoryServer.business.responses.create.CreateModelResponse;
import com.kodlamaio.invantoryServer.business.responses.get.GetAllModelResponse;
import com.kodlamaio.invantoryServer.business.responses.get.GetModelResponse;
import com.kodlamaio.invantoryServer.business.responses.update.UpdateModelResponse;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/models")
@AllArgsConstructor
public class ModelsController {
	private ModelService modelService;

	@GetMapping
	public List<GetAllModelResponse> getAll() {
		return this.modelService.getAll();
	}

	@PostMapping
	public CreateModelResponse add(@RequestBody @Valid CreateModelRequest createBrandRequest) {
		return this.modelService.add(createBrandRequest);
	}

	@DeleteMapping("/{id}")
	public void delete(@PathVariable String id) {
		this.modelService.delete(id);
	}

	@PutMapping
	public UpdateModelResponse update(@RequestBody @Valid UpdateModelRequest updateModelRequest) {
		return this.modelService.update(updateModelRequest);
	}

	@GetMapping("/{id}")
	public GetModelResponse getById(@PathVariable String id) {
		return this.modelService.getById(id);
	}
}
