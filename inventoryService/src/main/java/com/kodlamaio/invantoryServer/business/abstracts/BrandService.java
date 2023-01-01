package com.kodlamaio.invantoryServer.business.abstracts;

import java.util.List;

import com.kodlamaio.common.utilities.results.DataResult;
import com.kodlamaio.common.utilities.results.Result;
import com.kodlamaio.invantoryServer.business.requests.create.CreateBrandRequest;
import com.kodlamaio.invantoryServer.business.requests.update.UpdateBrandRequest;
import com.kodlamaio.invantoryServer.business.responses.create.CreateBrandResponse;
import com.kodlamaio.invantoryServer.business.responses.get.GetAllBrandResponse;
import com.kodlamaio.invantoryServer.business.responses.get.GetBrandResponse;
import com.kodlamaio.invantoryServer.business.responses.update.UpdateBrandResponse;

public interface BrandService {
	Result delete(String id);

	DataResult<UpdateBrandResponse> update(UpdateBrandRequest updateBrandRequest);

	DataResult<List<GetAllBrandResponse>> getAll();

	DataResult<CreateBrandResponse> add(CreateBrandRequest createBrandRequest);

	DataResult<GetBrandResponse> getById(String id);
}
