package com.kodlamaio.rentalservice.api;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kodlamaio.rentalservice.business.abstracts.RentalService;
import com.kodlamaio.rentalservice.business.requests.created.CreateRentalRequest;
import com.kodlamaio.rentalservice.business.requests.updated.UpdateRentalRequest;
import com.kodlamaio.rentalservice.business.responses.created.CreateRentalResponse;
import com.kodlamaio.rentalservice.business.responses.get.GetAllRentalResponse;
import com.kodlamaio.rentalservice.business.responses.updated.UpdateRentalResponse;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/rentals")
@AllArgsConstructor
public class RentalsController {
	private RentalService rentalService;

	@PostMapping
	public CreateRentalResponse add(@RequestBody CreateRentalRequest createRentalRequest) {
		return rentalService.add(createRentalRequest);
	}

	@PutMapping
	public UpdateRentalResponse update(@RequestBody UpdateRentalRequest updateRentalRequest) {
		return rentalService.update(updateRentalRequest);
	}

	@GetMapping
	public List<GetAllRentalResponse> getAll() {
		return rentalService.getAll();
	}

}
