package com.kodlamaio.filterService.api.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kodlamaio.common.utilities.results.DataResult;
import com.kodlamaio.filterService.business.abstracts.FilterService;
import com.kodlamaio.filterService.business.responses.get.GetAllFiltersResponse;
import com.kodlamaio.filterService.business.responses.get.GetFiltersResponse;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/filters/")
@AllArgsConstructor
public class FilterController {
	private FilterService filterService;

	@GetMapping
	public ResponseEntity<?> getAll() {
		DataResult<List<GetAllFiltersResponse>> result = this.filterService.getAll();
		if (result.isSuccess()) {
			return ResponseEntity.ok(result);
		}
		return ResponseEntity.badRequest().body(result);
	}

	@GetMapping("{brandName}")
	public ResponseEntity<?> getByBrandName(@PathVariable String brandName) {
		DataResult<List<GetAllFiltersResponse>> result = this.filterService.getByBrandName(brandName);
		if (result.isSuccess()) {
			return ResponseEntity.ok(result);
		}
		return ResponseEntity.badRequest().body(result);
	}

	@GetMapping("{modelName}")
	public ResponseEntity<?> getByModelName(@PathVariable String modelName) {
		DataResult<List<GetAllFiltersResponse>> result = this.filterService.getByModelName(modelName);
		if (result.isSuccess()) {
			return ResponseEntity.ok(result);
		}
		return ResponseEntity.badRequest().body(result);
	}

	@GetMapping("{plate}")
	public ResponseEntity<?> getByPlate(@PathVariable String plate) {
		DataResult<GetFiltersResponse> result = this.filterService.getByPlate(plate);
		if (result.isSuccess()) {
			return ResponseEntity.ok(result);
		}
		return ResponseEntity.badRequest().body(result);
	}

	@GetMapping("dailyprice/{min}-{max}")
	public ResponseEntity<?> getByDailyPrice(@PathVariable double min, @PathVariable double max) {
		DataResult<List<GetAllFiltersResponse>> result = this.filterService.getByDailyPrice(min, max);
		if (result.isSuccess()) {
			return ResponseEntity.ok(result);
		}
		return ResponseEntity.badRequest().body(result);
	}

	@GetMapping("modelyear/{min}-{max}")
	public ResponseEntity<?> getByModelYear(@PathVariable int min, @PathVariable int max) {
		DataResult<List<GetAllFiltersResponse>> result = this.filterService.getByModelYear(min, max);
		if (result.isSuccess()) {
			return ResponseEntity.ok(result);
		}
		return ResponseEntity.badRequest().body(result);
	}

}
