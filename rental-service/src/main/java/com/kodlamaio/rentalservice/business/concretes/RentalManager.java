package com.kodlamaio.rentalservice.business.concretes;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.kodlamaio.common.events.RentalCreatedEvent;
import com.kodlamaio.common.events.RentalUpdateEvent;
import com.kodlamaio.common.utilities.exceptions.BusinessException;
import com.kodlamaio.common.utilities.mapping.ModelMapperService;
import com.kodlamaio.invantoryServer.business.responses.get.GetCarResponse;
import com.kodlamaio.rentalservice.business.abstracts.RentalService;
import com.kodlamaio.rentalservice.business.requests.created.CreateRentalRequest;
import com.kodlamaio.rentalservice.business.requests.updated.UpdateRentalRequest;
import com.kodlamaio.rentalservice.business.responses.created.CreateRentalResponse;
import com.kodlamaio.rentalservice.business.responses.updated.UpdateRentalResponse;
import com.kodlamaio.rentalservice.dataAccess.RentalRepository;
import com.kodlamaio.rentalservice.entities.Rental;
import com.kodlamaio.rentalservice.inventoryServiceClient.InventoryClient;
import com.kodlamaio.rentalservice.kafka.producers.RentalProducer;
import com.kodlamaio.rentalservice.kafka.producers.RentalUpdateProducer;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class RentalManager implements RentalService {
	private RentalRepository rentalRepository;
	private ModelMapperService modelMapperService;
	private RentalProducer rentalProducer;
	private RentalUpdateProducer rentalUpdateProducer;
	private InventoryClient inventoryClient;

	@Override
	public CreateRentalResponse add(CreateRentalRequest createRentalRequest) {
		checkIfRentalExistsState(createRentalRequest.getCarId());
		Rental rental = this.modelMapperService.forRequest().map(createRentalRequest, Rental.class);
		rental.setId(UUID.randomUUID().toString());
		rental.setDateStartedId(LocalDateTime.now());
		double totalPrice = createRentalRequest.getDailyPrice() * createRentalRequest.getRentedForDays();
		rental.setTotalPrice(totalPrice);
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
		Rental rental = this.modelMapperService.forRequest().map(updateRentalRequest, Rental.class);
		rental.setDateStartedId(LocalDateTime.now());
		double totalPrice = updateRentalRequest.getDailyPrice() * updateRentalRequest.getRentedForDays();
		rental.setTotalPrice(totalPrice);
		 this.rentalRepository.save(rental);

		RentalUpdateEvent rentalUpdateEvent = new RentalUpdateEvent();
		rentalUpdateEvent.setCarNewId(updateRentalRequest.getCarId());
		Rental rentalOldId=this.rentalRepository.findById(updateRentalRequest.getCarId()).get();
		rentalUpdateEvent.setCarOldId(rentalOldId.getCarId());
		rentalUpdateEvent.setMessage("Rental Updated");

		this.rentalUpdateProducer.sendMessage(rentalUpdateEvent);

		UpdateRentalResponse updateRentalResponse = this.modelMapperService.forResponse().map(rental,
				UpdateRentalResponse.class);

		return updateRentalResponse;
	}
	
	private void checkIfRentalExistsState(String carId) {
		GetCarResponse getCarResponse=inventoryClient.getById(carId);
		if(getCarResponse.getState()==2) {
			throw new BusinessException("this car has been rented");
		}
	}

}
