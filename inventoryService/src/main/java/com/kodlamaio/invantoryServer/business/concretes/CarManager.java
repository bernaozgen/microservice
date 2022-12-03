package com.kodlamaio.invantoryServer.business.concretes;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.kodlamaio.common.utilities.exceptions.BusinessException;
import com.kodlamaio.common.utilities.mapping.ModelMapperService;
import com.kodlamaio.invantoryServer.business.abstracts.CarService;
import com.kodlamaio.invantoryServer.business.requests.create.CreateCarRequest;
import com.kodlamaio.invantoryServer.business.requests.update.UpdateCarRequest;
import com.kodlamaio.invantoryServer.business.responses.create.CreateCarResponse;
import com.kodlamaio.invantoryServer.business.responses.get.GetAllCarResponse;
import com.kodlamaio.invantoryServer.business.responses.get.GetCarResponse;
import com.kodlamaio.invantoryServer.business.responses.update.UpdateCarResponse;
import com.kodlamaio.invantoryServer.dataAccess.CarRepository;
import com.kodlamaio.invantoryServer.entites.Car;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CarManager implements CarService {
	private CarRepository carRepository;
	private ModelMapperService modelMapperService;

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

		this.carRepository.save(car);

		CreateCarResponse createCarResponse = this.modelMapperService.forResponse().map(car, CreateCarResponse.class);
		return createCarResponse;
	}

	@Override
	public UpdateCarResponse update(UpdateCarRequest updateCarRequest) {
		checkIfCarExistByPlate(updateCarRequest.getPlate());
		checkIfCarExistById(updateCarRequest.getId());
		Car car = this.modelMapperService.forRequest().map(updateCarRequest, Car.class);
		this.carRepository.save(car);
		UpdateCarResponse updateCarResponse = this.modelMapperService.forResponse().map(car, UpdateCarResponse.class);
		return updateCarResponse;
	}

	@Override
	public void delete(String id) {
		checkIfCarExistById(id);
		this.carRepository.deleteById(id);

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

	@Override
	public void updateCarState(String carId) {
		Car car = carRepository.findById(carId).get();
		car.setState(2);
		carRepository.save(car);

	}

	@Override
	public GetCarResponse getById(String carId) {
		Car car = this.carRepository.findById(carId).get();

		GetCarResponse response = this.modelMapperService.forResponse().map(car, GetCarResponse.class);

		return response;
	}
}
