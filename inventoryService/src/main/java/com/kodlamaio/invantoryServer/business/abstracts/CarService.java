package com.kodlamaio.invantoryServer.business.abstracts;

import java.util.List;

import com.kodlamaio.common.utilities.results.DataResult;
import com.kodlamaio.common.utilities.results.Result;
import com.kodlamaio.invantoryServer.business.requests.create.CreateCarRequest;
import com.kodlamaio.invantoryServer.business.requests.update.UpdateCarRequest;
import com.kodlamaio.invantoryServer.business.responses.create.CreateCarResponse;
import com.kodlamaio.invantoryServer.business.responses.get.GetAllCarResponse;
import com.kodlamaio.invantoryServer.business.responses.get.GetCarResponse;
import com.kodlamaio.invantoryServer.business.responses.update.UpdateCarResponse;

public interface CarService {
	DataResult<UpdateCarResponse> update(UpdateCarRequest updateCarRequest);

	Result delete(String id);

	DataResult<List<GetAllCarResponse>> getAll();

	DataResult<CreateCarResponse> add(CreateCarRequest createCarRequest);

	DataResult<GetCarResponse> getById(String carId);

	void updateCarState(String carId, int state);

}
