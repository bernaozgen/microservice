package com.kodlamaio.inventoryService.business.abstracts;

import java.util.List;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.kodlamaio.common.utilities.results.DataResult;
import com.kodlamaio.common.utilities.results.Result;
import com.kodlamaio.inventoryService.business.requests.create.CreateModelRequest;
import com.kodlamaio.inventoryService.business.requests.update.UpdateModelRequest;
import com.kodlamaio.inventoryService.business.responses.create.CreateModelResponse;
import com.kodlamaio.inventoryService.business.responses.get.GetAllModelResponse;
import com.kodlamaio.inventoryService.business.responses.get.GetModelResponse;
import com.kodlamaio.inventoryService.business.responses.update.UpdateModelResponse;

public interface ModelService {
	DataResult<List<GetAllModelResponse>> getAll();

	DataResult<CreateModelResponse> add(@RequestBody @Valid CreateModelRequest createBrandRequest);

	Result delete(@PathVariable String id);

	DataResult<UpdateModelResponse> update(@RequestBody @Valid UpdateModelRequest updateModelRequest);

	DataResult<GetModelResponse> getById(@PathVariable String id);

}
