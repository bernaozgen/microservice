package com.kodlamaio.inventoryService.business.abstracts;

import java.util.List;

import com.kodlamaio.common.utilities.results.DataResult;
import com.kodlamaio.common.utilities.results.Result;
import com.kodlamaio.inventoryService.business.requests.create.CreateCarRequest;
import com.kodlamaio.inventoryService.business.requests.update.UpdateCarRequest;
import com.kodlamaio.inventoryService.business.responses.create.CreateCarResponse;
import com.kodlamaio.inventoryService.business.responses.get.GetAllCarResponse;
import com.kodlamaio.inventoryService.business.responses.get.GetCarResponse;
import com.kodlamaio.inventoryService.business.responses.update.UpdateCarResponse;

public interface CarService {
	DataResult<UpdateCarResponse> update(UpdateCarRequest updateCarRequest);

	Result delete(String id);

	DataResult<List<GetAllCarResponse>> getAll();

	DataResult<CreateCarResponse> add(CreateCarRequest createCarRequest);

	GetCarResponse getById(String carId);

	void updateCarState(String carId, int state);


}
