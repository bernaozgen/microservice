package com.kodlamaio.filterService.business.abstracts;

import java.util.List;

import com.kodlamaio.common.events.inventory.brand.BrandUpdatedEvent;
import com.kodlamaio.common.events.inventory.car.CarCreatedEvent;
import com.kodlamaio.common.events.inventory.car.CarDeletedEvent;
import com.kodlamaio.common.events.inventory.car.CarUpdatedEvent;
import com.kodlamaio.common.events.inventory.model.ModelUpdatedEvent;
import com.kodlamaio.common.utilities.results.DataResult;
import com.kodlamaio.filterService.business.responses.get.GetAllFiltersResponse;
import com.kodlamaio.filterService.business.responses.get.GetFiltersResponse;

public interface FilterService {
	DataResult<List<GetAllFiltersResponse>> getAll();
	DataResult<List<GetAllFiltersResponse>> getByBrandName(String brandName);
	DataResult<List<GetAllFiltersResponse>> getByModelName(String modelName);
	DataResult<GetFiltersResponse> getByPlate(String plate);
	DataResult<List<GetAllFiltersResponse>> getByDailyPrice(double min,double max);
	DataResult<List<GetAllFiltersResponse>> getByModelYear(int min, int max);
	
	void addCar(CarCreatedEvent carCreatedEvent);
	void deleteCar(CarDeletedEvent carDeletedEvent);
	void updateCar(CarUpdatedEvent carUpdatedEvent);
	
	void updateBrand(BrandUpdatedEvent brandUpdatedEvent);
	void updateModel(ModelUpdatedEvent modelUpdatedEvent);
}
