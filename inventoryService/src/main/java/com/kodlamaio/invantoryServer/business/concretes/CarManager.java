package com.kodlamaio.invantoryServer.business.concretes;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.kodlamaio.common.events.inventory.car.CarCreatedEvent;
import com.kodlamaio.common.events.inventory.car.CarDeletedEvent;
import com.kodlamaio.common.events.inventory.car.CarUpdatedEvent;
import com.kodlamaio.common.utilities.exceptions.BusinessException;
import com.kodlamaio.common.utilities.mapping.ModelMapperService;
import com.kodlamaio.invantoryServer.business.abstracts.CarService;
import com.kodlamaio.invantoryServer.business.abstracts.ModelService;
import com.kodlamaio.invantoryServer.business.requests.create.CreateCarRequest;
import com.kodlamaio.invantoryServer.business.requests.update.UpdateCarRequest;
import com.kodlamaio.invantoryServer.business.responses.create.CreateCarResponse;
import com.kodlamaio.invantoryServer.business.responses.get.GetAllCarResponse;
import com.kodlamaio.invantoryServer.business.responses.get.GetCarResponse;
import com.kodlamaio.invantoryServer.business.responses.update.UpdateCarResponse;
import com.kodlamaio.invantoryServer.dataAccess.CarRepository;
import com.kodlamaio.invantoryServer.entites.Car;
import com.kodlamaio.invantoryServer.kafka.InventoryProducer;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CarManager implements CarService {
	private CarRepository carRepository;
	private ModelMapperService modelMapperService;
	private InventoryProducer inventoryProducer;
	private ModelService modelService;

	@Override
	public List<GetAllCarResponse> getAll() {
		List<Car> cars = this.carRepository.findAll();

		List<GetAllCarResponse> response = cars.stream()
				.map(car -> this.modelMapperService.forResponse().map(car, GetAllCarResponse.class))
				.collect(Collectors.toList());

		return response;
	}

	@Override
	public CreateCarResponse add(CreateCarRequest createCarRequest) {
		checkIfCarExistByPlate(createCarRequest.getPlate());

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
		return createCarResponse;
	}

	@Override
	public UpdateCarResponse update(UpdateCarRequest updateCarRequest) {
		checkIfCarExistByPlate(updateCarRequest.getPlate());
		checkIfCarExistById(updateCarRequest.getId());

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
		return updateCarResponse;
	}

	@Override
	public void delete(String id) {
		checkIfCarExistById(id);

		this.carRepository.deleteById(id);
		CarDeletedEvent carDeletedEvent = new CarDeletedEvent();
		carDeletedEvent.setMessage("Car Deleted");
		carDeletedEvent.setCarId(id);
		this.inventoryProducer.sendMessage(carDeletedEvent);

	}

	@Override
	public void updateCarState(String carId, int state) {
		Car car = carRepository.findById(carId).get();
		car.setState(state);
		carRepository.save(car);

	}

	@Override
	public GetCarResponse getById(String carId) {
		Car car = this.carRepository.findById(carId).get();

		GetCarResponse response = this.modelMapperService.forResponse().map(car, GetCarResponse.class);

		return response;
	}

	private void checkIfCarExistById(String id) {
		var result = this.carRepository.findById(id);
		if (result == null) {
			throw new BusinessException("CAR.NOT.EXİSTS");
		}

	}

	private void checkIfCarExistByPlate(String plate) {
		var result = this.carRepository.findByPlate(plate);
		if (result != null) {
			throw new BusinessException("PLATE.EXİSTS");
		}
	}
//	private void checkIfModelExistByModelId(String modelId) {
//		var result =this.modelService
//	}

}
