package com.kodlamaio.rentalservice.api;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kodlamaio.common.utilities.results.DataResult;
import com.kodlamaio.common.utilities.results.Result;
import com.kodlamaio.rentalservice.business.abstracts.RentalService;
import com.kodlamaio.rentalservice.business.requests.created.CreatePaymentRequest;
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
	public ResponseEntity<?> add(@RequestBody @Valid CreateRentalRequest createRentalRequest,@RequestParam String cardNo, @RequestParam String cardHolder,
            @RequestParam String cvv) {
		CreatePaymentRequest createPaymentRequest = new CreatePaymentRequest();
		createPaymentRequest.setCardHolder(cardHolder);
		createPaymentRequest.setCardNo(cardNo);
		createPaymentRequest.setCvv(cvv);
		DataResult<CreateRentalResponse> result = this.rentalService.add(createRentalRequest,createPaymentRequest);
		if (result.isSuccess()) {
			return ResponseEntity.ok(result);
		}
		return ResponseEntity.badRequest().body(result);
	}

	@PutMapping
	public ResponseEntity<?> update(@RequestBody  @Valid UpdateRentalRequest updateRentalRequest) {
		DataResult<UpdateRentalResponse> result = this.rentalService.update(updateRentalRequest);
		if (result.isSuccess()) {
			return ResponseEntity.ok(result);
		}
		return ResponseEntity.badRequest().body(result);
	}

	@GetMapping
	public ResponseEntity<?> getAll() {
		DataResult<List<GetAllRentalResponse>> result = this.rentalService.getAll();
		if (result.isSuccess()) {
			return ResponseEntity.ok(result);
		}
		return ResponseEntity.badRequest().body(result);

	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable String id) {
		Result result = this.rentalService.delete(id);
		if (result.isSuccess()) {
			return ResponseEntity.ok(result);
		}
		return ResponseEntity.badRequest().body(result);
	}
	
	
}
