package com.kodlamaio.invantoryServer.business.abstracts;

import java.util.List;

import com.kodlamaio.invantoryServer.business.requests.create.CreateBrandRequest;
import com.kodlamaio.invantoryServer.business.requests.update.UpdateBrandRequest;
import com.kodlamaio.invantoryServer.business.responses.create.CreateBrandResponse;
import com.kodlamaio.invantoryServer.business.responses.get.GetAllBrandResponse;
import com.kodlamaio.invantoryServer.business.responses.update.UpdateBrandResponse;

public interface BrandServise {
	void delete(String id);

	UpdateBrandResponse update(UpdateBrandRequest updateBrandRequest);

	List<GetAllBrandResponse> getAll();

	CreateBrandResponse add(CreateBrandRequest createBrandRequest);
}
