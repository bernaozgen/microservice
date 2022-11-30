package com.kodlamaio.invantoryServer.business.abstracts;

import java.util.List;

import com.kodlamaio.invantoryServer.business.requests.create.CreateModelRequest;
import com.kodlamaio.invantoryServer.business.requests.update.UpdateModelRequest;
import com.kodlamaio.invantoryServer.business.responses.create.CreateModelResponse;
import com.kodlamaio.invantoryServer.business.responses.get.GetAllModelResponse;
import com.kodlamaio.invantoryServer.business.responses.update.UpdateModelResponse;

public interface ModelService {
	List<GetAllModelResponse> getAll();

	CreateModelResponse add(CreateModelRequest createBrandRequest);

	void delete(String id);

	UpdateModelResponse update(UpdateModelRequest updateModelRequest);
}
