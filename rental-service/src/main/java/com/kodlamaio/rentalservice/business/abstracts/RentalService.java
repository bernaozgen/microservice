package com.kodlamaio.rentalservice.business.abstracts;

import java.util.List;

import com.kodlamaio.rentalservice.business.requests.created.CreateRentalRequest;
import com.kodlamaio.rentalservice.business.requests.updated.UpdateRentalRequest;
import com.kodlamaio.rentalservice.business.responses.created.CreateRentalResponse;
import com.kodlamaio.rentalservice.business.responses.get.GetAllRentalResponse;
import com.kodlamaio.rentalservice.business.responses.updated.UpdateRentalResponse;

public interface RentalService {
	CreateRentalResponse add(CreateRentalRequest createRentalRequest);

	UpdateRentalResponse update(UpdateRentalRequest updateRentalRequest);

	List<GetAllRentalResponse> getAll();

	void setConditionByPayment(String id);

}
