package com.kodlamaio.rentalservice.business.concretes;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.kodlamaio.common.events.rentals.RentalCreatedEvent;
import com.kodlamaio.common.events.rentals.RentalUpdateEvent;
import com.kodlamaio.common.rentalPayment.PayMoneyRequest;
import com.kodlamaio.common.utilities.exceptions.BusinessException;
import com.kodlamaio.common.utilities.mapping.ModelMapperService;
import com.kodlamaio.invantoryServer.business.responses.get.GetCarResponse;
import com.kodlamaio.rentalservice.business.abstracts.RentalService;
import com.kodlamaio.rentalservice.business.requests.created.CreateRentalRequest;
import com.kodlamaio.rentalservice.business.requests.updated.UpdateRentalRequest;
import com.kodlamaio.rentalservice.business.responses.created.CreateRentalResponse;
import com.kodlamaio.rentalservice.business.responses.get.GetAllRentalResponse;
import com.kodlamaio.rentalservice.business.responses.updated.UpdateRentalResponse;
import com.kodlamaio.rentalservice.clients.InventoryClient;
import com.kodlamaio.rentalservice.clients.PaymentClient;
import com.kodlamaio.rentalservice.dataAccess.RentalRepository;
import com.kodlamaio.rentalservice.entities.Rental;
import com.kodlamaio.rentalservice.kafka.RentalProducer;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class RentalManager implements RentalService {
	private RentalRepository rentalRepository;
	private ModelMapperService modelMapperService;
	private RentalProducer rentalProducer;
	private PaymentClient paymentClient;
	private InventoryClient inventoryClient;

	@Override
	public CreateRentalResponse add(CreateRentalRequest createRentalRequest) {

		checkIfRentalExistsState(createRentalRequest.getCarId());
		Rental rental = this.modelMapperService.forRequest().map(createRentalRequest, Rental.class);
		rental.setId(UUID.randomUUID().toString());
		rental.setDateStarted(LocalDateTime.now());
		double totalPrice = createRentalRequest.getDailyPrice() * createRentalRequest.getRentedForDays();
		rental.setTotalPrice(totalPrice);

		PayMoneyRequest payMoneyRequest = new PayMoneyRequest();
		payMoneyRequest.setRentalId(rental.getId());
		payMoneyRequest.setCardNo(createRentalRequest.getCardNo());
		payMoneyRequest.setCardHolder(createRentalRequest.getCardHolder());
		payMoneyRequest.setTotalPrice(rental.getTotalPrice());
		payMoneyRequest.setBalance(createRentalRequest.getBalance());

		paymentClient.add(payMoneyRequest);
		 rental.setCondition(1);// satış gerçekleşti id si
		Rental rentalCreated = this.rentalRepository.save(rental);

		RentalCreatedEvent rentalCreatedEvent = new RentalCreatedEvent();
		rentalCreatedEvent.setCarId(rentalCreated.getCarId());
		rentalCreatedEvent.setMessage("Rental Created");

		this.rentalProducer.sendMessage(rentalCreatedEvent);

		CreateRentalResponse createRentalResponse = this.modelMapperService.forResponse().map(rental,
				CreateRentalResponse.class);
		return createRentalResponse;
	}

	@Override
	public UpdateRentalResponse update(UpdateRentalRequest updateRentalRequest) {
		

		Rental rental = this.rentalRepository.findByCarId(updateRentalRequest.getOldCarId());
		rental.setCarId(updateRentalRequest.getNewCarId());
		rental.setDateStarted(LocalDateTime.now());
		rental.setDailyPrice(updateRentalRequest.getDailyPrice());
		rental.setRentedForDays(updateRentalRequest.getRentedForDays());
		double totalPrice = updateRentalRequest.getDailyPrice() * updateRentalRequest.getRentedForDays();
		rental.setTotalPrice(totalPrice);
		Rental rentalUpdated = this.rentalRepository.save(rental);

		RentalUpdateEvent rentalUpdateEvent = new RentalUpdateEvent();

		rentalUpdateEvent.setCarNewId(updateRentalRequest.getOldCarId());
		rentalUpdateEvent.setCarNewId(updateRentalRequest.getNewCarId());
		rentalUpdateEvent.setMessage("Rental Updated");

		this.rentalProducer.sendMessage(rentalUpdateEvent);

		UpdateRentalResponse updateRentalResponse = this.modelMapperService.forResponse().map(rental,
				UpdateRentalResponse.class);
		updateRentalResponse.setCarId(rentalUpdated.getCarId());

		return updateRentalResponse;
	}

	private void checkIfRentalExistsState(String carId) {
		GetCarResponse getCarResponse = inventoryClient.getById(carId);
		if (getCarResponse.getState() != 1) {
			throw new BusinessException("this car has been rented");
		}
	}

	@Override
	public void setConditionByPayment(String id) {// araba alındıktan sonra durumunu boşa cıkartıyor
		Rental rental = this.rentalRepository.findById(id).get();
		if (rental.getCondition() == 1) {
			rental.setCondition(2);
		}
		this.rentalRepository.save(rental);

	}

	@Override
	public List<GetAllRentalResponse> getAll() {
		List<Rental> rentals = this.rentalRepository.findAll();
		List<GetAllRentalResponse> getAllRentalResponses = rentals.stream()
				.map(rental -> this.modelMapperService.forResponse().map(rentals, GetAllRentalResponse.class))
				.collect(Collectors.toList());
		return getAllRentalResponses;
	}

}
