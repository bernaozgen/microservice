package com.kodlamaio.inventoryService.business.concretes;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.kodlamaio.common.events.inventory.car.CarCreatedEvent;
import com.kodlamaio.common.events.inventory.car.CarDeletedEvent;
import com.kodlamaio.common.events.inventory.car.CarUpdatedEvent;
import com.kodlamaio.common.utilities.exceptions.BusinessException;
import com.kodlamaio.common.utilities.mapping.ModelMapperService;
import com.kodlamaio.common.utilities.results.DataResult;
import com.kodlamaio.common.utilities.results.Result;
import com.kodlamaio.common.utilities.results.SuccessDataResult;
import com.kodlamaio.common.utilities.results.SuccessResult;
import com.kodlamaio.inventoryService.business.abstracts.CarService;
import com.kodlamaio.inventoryService.business.abstracts.ModelService;
import com.kodlamaio.inventoryService.business.constants.Messages;
import com.kodlamaio.inventoryService.business.requests.create.CreateCarRequest;
import com.kodlamaio.inventoryService.business.requests.update.UpdateCarRequest;
import com.kodlamaio.inventoryService.business.responses.create.CreateCarResponse;
import com.kodlamaio.inventoryService.business.responses.get.GetAllCarResponse;
import com.kodlamaio.inventoryService.business.responses.get.GetCarResponse;
import com.kodlamaio.inventoryService.business.responses.update.UpdateCarResponse;
import com.kodlamaio.inventoryService.dataAccess.CarRepository;
import com.kodlamaio.inventoryService.entites.Car;
import com.kodlamaio.inventoryService.kafka.InventoryProducer;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CarManager implements CarService {
	private CarRepository carRepository;
	private ModelMapperService modelMapperService;
	private InventoryProducer inventoryProducer;
	private ModelService modelService;

	@Override
	public DataResult<List<GetAllCarResponse>> getAll() {
		List<Car> cars = this.carRepository.findAll();

		List<GetAllCarResponse> response = cars.stream()
				.map(car -> this.modelMapperService.forResponse().map(car, GetAllCarResponse.class))
				.collect(Collectors.toList());

		return new SuccessDataResult<List<GetAllCarResponse>>(response, Messages.CarListed);
	}

	@Override
	public DataResult<CreateCarResponse> add(CreateCarRequest createCarRequest) {
		checkIfCarExistByPlate(createCarRequest.getPlate());
		checkIfModelExistByModelId(createCarRequest.getModelId());
		Car car = this.modelMapperService.forRequest().map(createCarRequest, Car.class);
		car.setId(UUID.randomUUID().toString());// unig sayı uretir
		car.setState(1);
		Car createdCar = this.carRepository.save(car);

		CarCreatedEvent carCreatedEvent = this.modelMapperService.forRequest().map(createdCar, CarCreatedEvent.class);
		carCreatedEvent.setCarId(createdCar.getId());
		carCreatedEvent.setModelId(createdCar.getModel().getId());
		carCreatedEvent.setModelName(createdCar.getModel().getName());
		carCreatedEvent.setBrandId(createdCar.getModel().getBrand().getId());
		carCreatedEvent.setBrandName(createdCar.getModel().getBrand().getName());
		carCreatedEvent.setMessage("Car Created");

		this.inventoryProducer.sendMessage(carCreatedEvent);

		CreateCarResponse createCarResponse = this.modelMapperService.forResponse().map(car, CreateCarResponse.class);
		return new SuccessDataResult<CreateCarResponse>(createCarResponse, Messages.CarCreated);
	}

	@Override
	public DataResult<UpdateCarResponse> update(UpdateCarRequest updateCarRequest) {
		checkIfCarExistByPlate(updateCarRequest.getPlate());
		checkIfCarExistById(updateCarRequest.getId());
		checkIfModelExistByModelId(updateCarRequest.getModelId());

		Car car = this.modelMapperService.forRequest().map(updateCarRequest, Car.class);
		car.setState(1);

		Car updatedCar = this.carRepository.save(car);
		CarUpdatedEvent carUpdatedEvent = this.modelMapperService.forRequest().map(updatedCar, CarUpdatedEvent.class);
		carUpdatedEvent.setCarId(updatedCar.getId());
		carUpdatedEvent.setModelId(updatedCar.getModel().getId());
		carUpdatedEvent.setModelName(updatedCar.getModel().getName());
		carUpdatedEvent.setBrandId(updatedCar.getModel().getBrand().getId());
		carUpdatedEvent.setBrandName(updatedCar.getModel().getBrand().getName());
		carUpdatedEvent.setMessage("Car Updated");
		this.inventoryProducer.sendMessage(carUpdatedEvent);

		UpdateCarResponse updateCarResponse = this.modelMapperService.forResponse().map(car, UpdateCarResponse.class);
		return new SuccessDataResult<UpdateCarResponse>(updateCarResponse, Messages.CarUpdated);
	}

	@Override
	public Result delete(String id) {
		checkIfCarExistById(id);

		this.carRepository.deleteById(id);
		CarDeletedEvent carDeletedEvent = new CarDeletedEvent();
		carDeletedEvent.setMessage("Car Deleted");
		carDeletedEvent.setCarId(id);
		this.inventoryProducer.sendMessage(carDeletedEvent);
		return new SuccessResult(Messages.CarDeleted);
	}

	@Override
	public void updateCarState(String carId, int state) {
		checkIfCarExistById(carId);
		
		Car car = carRepository.findById(carId).get();
		car.setState(state);
		carRepository.save(car);

	}

	@Override
	public GetCarResponse getById(String carId) {
		checkIfCarExistById(carId);
		Car car = this.carRepository.findById(carId).get();

		GetCarResponse response = this.modelMapperService.forResponse().map(car, GetCarResponse.class);

		return response;
	}

	private void checkIfCarExistById(String id) {
		var result = this.carRepository.findById(id);
		if (result == null) {
			throw new BusinessException(Messages.CarIdNotFound);
		}

	}

	private void checkIfCarExistByPlate(String plate) {
		var result = this.carRepository.findByPlate(plate);
		if (result != null) {
			throw new BusinessException(Messages.PlateExist);
		}
	}

	private void checkIfModelExistByModelId(String modelId) {
		var result = this.modelService.getById(modelId);
		if (result == null) {
			throw new BusinessException(Messages.ModelIdNotFound);
		}
	}



}
