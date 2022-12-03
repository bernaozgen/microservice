package com.kodlamaio.invantoryServer.business.abstracts;

import java.util.List;

import com.kodlamaio.invantoryServer.business.requests.create.CreateCarRequest;
import com.kodlamaio.invantoryServer.business.requests.update.UpdateCarRequest;
import com.kodlamaio.invantoryServer.business.responses.create.CreateCarResponse;
import com.kodlamaio.invantoryServer.business.responses.get.GetAllCarResponse;
import com.kodlamaio.invantoryServer.business.responses.get.GetCarResponse;
import com.kodlamaio.invantoryServer.business.responses.update.UpdateCarResponse;

public interface CarService {
	UpdateCarResponse update(UpdateCarRequest updateCarRequest);

	void delete(String id);

	List<GetAllCarResponse> getAll();

	CreateCarResponse add(CreateCarRequest createCarRequest);

	void updateCarState(String carId);

	GetCarResponse getById(String carId);

}
