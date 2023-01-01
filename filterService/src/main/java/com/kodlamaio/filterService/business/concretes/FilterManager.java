package com.kodlamaio.filterService.business.concretes;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.kodlamaio.common.events.inventory.brand.BrandUpdatedEvent;
import com.kodlamaio.common.events.inventory.car.CarCreatedEvent;
import com.kodlamaio.common.events.inventory.car.CarDeletedEvent;
import com.kodlamaio.common.events.inventory.car.CarUpdatedEvent;
import com.kodlamaio.common.events.inventory.model.ModelUpdatedEvent;
import com.kodlamaio.common.utilities.mapping.ModelMapperService;
import com.kodlamaio.common.utilities.results.DataResult;
import com.kodlamaio.common.utilities.results.SuccessDataResult;
import com.kodlamaio.filterService.business.abstracts.FilterService;
import com.kodlamaio.filterService.business.constants.Messages;
import com.kodlamaio.filterService.business.responses.get.GetAllFiltersResponse;
import com.kodlamaio.filterService.business.responses.get.GetFiltersResponse;
import com.kodlamaio.filterService.dataAccess.FilterRepository;
import com.kodlamaio.filterService.entities.Filter;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class FilterManager implements FilterService {
	private FilterRepository filterRepository;
	private ModelMapperService modelMapperService;

	@Override
	public DataResult<List<GetAllFiltersResponse>> getAll() {
		List<Filter> filters = filterRepository.findAll();
		List<GetAllFiltersResponse> responses = filters.stream()
				.map(filter -> this.modelMapperService.forRequest().map(filter, GetAllFiltersResponse.class))
				.collect(Collectors.toList());
		return new SuccessDataResult<List<GetAllFiltersResponse>>(responses ,Messages.FilterListed);
	}
	@Override
	public DataResult<List<GetAllFiltersResponse>> getByBrandName(String brandName) {
		List<Filter> filters = this.filterRepository.findByBrandName(brandName);
		List<GetAllFiltersResponse> responses = filters.stream()
				.map(filter -> this.modelMapperService.forResponse().map(filter, GetAllFiltersResponse.class))
				.collect(Collectors.toList());
		return new SuccessDataResult<List<GetAllFiltersResponse>>(responses , Messages.BrandNameListed);
	}
	@Override
	public DataResult<List<GetAllFiltersResponse>> getByModelName(String modelName) {
		List<Filter> filters = this.filterRepository.findByModelName(modelName);
		List<GetAllFiltersResponse> responses = filters.stream()
				.map(filter -> this.modelMapperService.forResponse().map(filter, GetAllFiltersResponse.class))
				.collect(Collectors.toList());
		
		return new SuccessDataResult<List<GetAllFiltersResponse>>(responses , Messages.ModelNameListed);
	}
	@Override
	public DataResult<GetFiltersResponse> getByPlate(String plate) {
		Filter filter = this.filterRepository.findByPlate(plate);
		GetFiltersResponse response = this.modelMapperService.forResponse().map(filter, GetFiltersResponse.class);
		return new SuccessDataResult<GetFiltersResponse>(response,Messages.Plate);
	}
	@Override
	public DataResult<List<GetAllFiltersResponse>> getByDailyPrice(double min, double max) {
		List<Filter> filters = this.filterRepository.findAll();
		List<GetAllFiltersResponse> getAllFiltersResponses = new ArrayList<GetAllFiltersResponse>();
		for (Filter filter : filters) {
			if (filter.getDailyPrice() < max && filter.getDailyPrice() > min) {
				GetAllFiltersResponse response = this.modelMapperService.forResponse().map(filter,
						GetAllFiltersResponse.class);
				getAllFiltersResponses.add(response);
			}
		}
		return new SuccessDataResult<List<GetAllFiltersResponse>>(getAllFiltersResponses,Messages.DailyPriceListed) ;
	}
	@Override
	public DataResult<List<GetAllFiltersResponse>> getByModelYear(int min, int max) {
		List<Filter> filters = this.filterRepository.findAll();
		List<GetAllFiltersResponse> getAllFiltersResponses = new ArrayList<GetAllFiltersResponse>();
		for (Filter filter : filters) {
			if (filter.getModelYear() < max && filter.getModelYear() > min) {
				GetAllFiltersResponse response = this.modelMapperService.forResponse().map(filter,
						GetAllFiltersResponse.class);
				getAllFiltersResponses.add(response);
			}
		}
		return new SuccessDataResult<List<GetAllFiltersResponse>>(getAllFiltersResponses,Messages.ModelYearListed);
	}

	@Override
	public void addCar(CarCreatedEvent carCreatedEvent) {
		Filter filter = this.modelMapperService.forRequest().map(carCreatedEvent, Filter.class);
		this.filterRepository.save(filter);
	}

	@Override
	public void deleteCar(CarDeletedEvent carDeletedEvent) {
		Filter filter = this.filterRepository.findByCarId(carDeletedEvent.getCarId());
		this.filterRepository.delete(filter);

	}

	@Override
	public void updateCar(CarUpdatedEvent carUpdatedEvent) {
		Filter filter = this.modelMapperService.forRequest().map(carUpdatedEvent, Filter.class);
		this.filterRepository.save(filter);
	}

	@Override
	public void updateBrand(BrandUpdatedEvent brandUpdatedEvent) {
		List<Filter> filters = this.filterRepository.findByBrandName(brandUpdatedEvent.getBrandName());
		for (Filter filter : filters) {
			filter.setBrandName(brandUpdatedEvent.getBrandName());
			filter.setBrandId(brandUpdatedEvent.getBrandId());
			this.filterRepository.save(filter);
		}
	}

	@Override
	public void updateModel(ModelUpdatedEvent modelUpdatedEvent) {
		List<Filter> filters = this.filterRepository.findByModelName(modelUpdatedEvent.getModelName());
		for (Filter filter : filters) {
			filter.setBrandName(modelUpdatedEvent.getBrandName());
			filter.setBrandId(modelUpdatedEvent.getBrandId());
			filter.setModelName(modelUpdatedEvent.getModelName());
			filter.setModelId(modelUpdatedEvent.getModelId());
			this.filterRepository.save(filter);
		}

	}
}
