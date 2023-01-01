package com.kodlamaio.rentalservice.business.abstracts;

import java.util.List;

import com.kodlamaio.common.utilities.results.DataResult;
import com.kodlamaio.common.utilities.results.Result;
import com.kodlamaio.rentalservice.business.requests.created.CreateRentalRequest;
import com.kodlamaio.rentalservice.business.requests.updated.UpdateRentalRequest;
import com.kodlamaio.rentalservice.business.responses.created.CreateRentalResponse;
import com.kodlamaio.rentalservice.business.responses.get.GetAllRentalResponse;
import com.kodlamaio.rentalservice.business.responses.updated.UpdateRentalResponse;

public interface RentalService {
	DataResult<CreateRentalResponse> add(CreateRentalRequest createRentalRequest);

	DataResult<UpdateRentalResponse> update(UpdateRentalRequest updateRentalRequest);

	DataResult<List<GetAllRentalResponse>> getAll();

	Result delete (String id);
	
	void setConditionByPayment(String id);

}
