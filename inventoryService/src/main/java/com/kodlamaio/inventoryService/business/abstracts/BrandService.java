package com.kodlamaio.inventoryService.business.abstracts;

import java.util.List;

import com.kodlamaio.common.utilities.results.DataResult;
import com.kodlamaio.common.utilities.results.Result;
import com.kodlamaio.inventoryService.business.requests.create.CreateBrandRequest;
import com.kodlamaio.inventoryService.business.requests.update.UpdateBrandRequest;
import com.kodlamaio.inventoryService.business.responses.create.CreateBrandResponse;
import com.kodlamaio.inventoryService.business.responses.get.GetAllBrandResponse;
import com.kodlamaio.inventoryService.business.responses.get.GetBrandResponse;
import com.kodlamaio.inventoryService.business.responses.update.UpdateBrandResponse;

public interface BrandService {
	Result delete(String id);

	DataResult<UpdateBrandResponse> update(UpdateBrandRequest updateBrandRequest);

	DataResult<List<GetAllBrandResponse>> getAll();

	DataResult<CreateBrandResponse> add(CreateBrandRequest createBrandRequest);

	DataResult<GetBrandResponse> getById(String id);
}
