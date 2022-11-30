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

import com.kodlamaio.invantoryServer.business.abstracts.CarService;
import com.kodlamaio.invantoryServer.business.requests.create.CreateCarRequest;
import com.kodlamaio.invantoryServer.business.requests.update.UpdateCarRequest;
import com.kodlamaio.invantoryServer.business.responses.create.CreateCarResponse;
import com.kodlamaio.invantoryServer.business.responses.get.GetAllCarResponse;
import com.kodlamaio.invantoryServer.business.responses.update.UpdateCarResponse;

import lombok.AllArgsConstructor;

@RequestMapping("/api/cars")
@RestController
@AllArgsConstructor
public class CarsController {
	private CarService carService;

	@PutMapping("/update")
	public UpdateCarResponse update(@RequestBody @Valid UpdateCarRequest updateCarRequest) {
		return this.carService.update(updateCarRequest);
	}

	@DeleteMapping("/{id}")
	public void delete(@PathVariable @Valid String id) {
		this.carService.delete(id);
	}

	@PostMapping("/add")
	public CreateCarResponse add(@RequestBody @Valid CreateCarRequest createCarRequest) {
		return this.carService.add(createCarRequest);
	}

	@GetMapping("/getall")
	public List<GetAllCarResponse> getAll() {
		return this.carService.getAll();
	}
}
