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

import com.kodlamaio.invantoryServer.business.abstracts.BrandServise;
import com.kodlamaio.invantoryServer.business.requests.create.CreateBrandRequest;
import com.kodlamaio.invantoryServer.business.requests.update.UpdateBrandRequest;
import com.kodlamaio.invantoryServer.business.responses.create.CreateBrandResponse;
import com.kodlamaio.invantoryServer.business.responses.get.GetAllBrandResponse;
import com.kodlamaio.invantoryServer.business.responses.update.UpdateBrandResponse;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/brands")
@AllArgsConstructor
public class BrandsController {
	private BrandServise brandServise;

	@GetMapping
	public List<GetAllBrandResponse> getAll() {
		return this.brandServise.getAll();
	}

	@PostMapping
	public CreateBrandResponse add(@RequestBody @Valid CreateBrandRequest createBrandRequest) {
		return this.brandServise.add(createBrandRequest);
	}

	@PutMapping
	public UpdateBrandResponse update(@RequestBody @Valid UpdateBrandRequest updateBrandRequest) {
		return this.brandServise.update(updateBrandRequest);
	}

	@DeleteMapping("/{id}")
	public void delete(@PathVariable String id) {
		this.brandServise.delete(id);
	}
}
