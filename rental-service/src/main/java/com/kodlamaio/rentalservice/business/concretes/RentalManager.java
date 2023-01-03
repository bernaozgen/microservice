package com.kodlamaio.rentalservice.business.concretes;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.kodlamaio.common.events.rentals.InvoiceCreatedEvent;
import com.kodlamaio.common.events.rentals.RentalCreatedEvent;
import com.kodlamaio.common.events.rentals.RentalUpdateEvent;
import com.kodlamaio.common.utilities.exceptions.BusinessException;
import com.kodlamaio.common.utilities.mapping.ModelMapperService;
import com.kodlamaio.common.utilities.results.DataResult;
import com.kodlamaio.common.utilities.results.Result;
import com.kodlamaio.common.utilities.results.SuccessDataResult;
import com.kodlamaio.common.utilities.results.SuccessResult;
import com.kodlamaio.rentalservice.business.abstracts.RentalService;
import com.kodlamaio.rentalservice.business.constants.Messages;
import com.kodlamaio.rentalservice.business.requests.created.CreatePaymentRequest;
import com.kodlamaio.rentalservice.business.requests.created.CreateRentalRequest;
import com.kodlamaio.rentalservice.business.requests.updated.UpdateRentalRequest;
import com.kodlamaio.rentalservice.business.responses.created.CreateRentalResponse;
import com.kodlamaio.rentalservice.business.responses.get.GetAllCarResponse;
import com.kodlamaio.rentalservice.business.responses.get.GetAllRentalResponse;
import com.kodlamaio.rentalservice.business.responses.updated.UpdateRentalResponse;
import com.kodlamaio.rentalservice.clients.InventoryClient;
import com.kodlamaio.rentalservice.clients.PaymentClient;
import com.kodlamaio.rentalservice.dataAccess.RentalRepository;
import com.kodlamaio.rentalservice.entities.Rental;
import com.kodlamaio.rentalservice.kafka.InvoiceProducer;
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
	private InvoiceProducer invoiceProducer;

	@Override
	public DataResult<CreateRentalResponse> add(CreateRentalRequest createRentalRequest,
			CreatePaymentRequest createPaymentRequest) {

		//checkIfRentalExistsState(createRentalRequest.getCarId());
		Rental rental = this.modelMapperService.forRequest().map(createRentalRequest, Rental.class);
		rental.setId(UUID.randomUUID().toString());
		double totalPrice = createRentalRequest.getDailyPrice() * createRentalRequest.getRentedForDays();
		rental.setTotalPrice(totalPrice);

		paymentClient.paymentReceived(createPaymentRequest.getCardNo(), createPaymentRequest.getCardHolder(),
				createPaymentRequest.getCvv(), createPaymentRequest.getCardDate(), rental.getTotalPrice());

		Rental rentalCreated = this.rentalRepository.save(rental);
		rental.setCondition(1);// satış gerçekleşti id si
		RentalCreatedEvent rentalCreatedEvent = new RentalCreatedEvent();
		rentalCreatedEvent.setCarId(rentalCreated.getCarId());
		rentalCreatedEvent.setMessage("Rental Created");

		this.rentalProducer.sendMessage(rentalCreatedEvent);

		InvoiceCreatedEvent invoiceCreatedEvent = new InvoiceCreatedEvent();
		invoiceCreatedEvent.setCarId(rental.getCarId());
		invoiceCreatedEvent.setFullName(createPaymentRequest.getCardHolder());
		invoiceCreatedEvent.setDailyPrice(rental.getDailyPrice());
		invoiceCreatedEvent.setTotalPrice(rental.getTotalPrice());
		invoiceCreatedEvent.setRentedForDays(rental.getRentedForDays());
		invoiceCreatedEvent.setRentedDate(rental.getDateStarted());
		invoiceProducer.sendMessage(invoiceCreatedEvent);

		CreateRentalResponse createRentalResponse = this.modelMapperService.forResponse().map(rental,
				CreateRentalResponse.class);
		return new SuccessDataResult<CreateRentalResponse>(createRentalResponse, Messages.RentalCreated);
	}

	@Override
	public DataResult<UpdateRentalResponse> update(UpdateRentalRequest updateRentalRequest) {
		checkIfRentalExistsId(updateRentalRequest.getId());
		RentalUpdateEvent rentalUpdatedEvent = new RentalUpdateEvent();

		Rental rental = this.rentalRepository.findById(updateRentalRequest.getId()).get();
		rentalUpdatedEvent.setOldCarId(rental.getCarId());

		rental.setCarId(updateRentalRequest.getCarId());
		rental.setDailyPrice(updateRentalRequest.getDailyPrice());
		rental.setRentedForDays(updateRentalRequest.getRentedForDays());
		double totalPrice = updateRentalRequest.getDailyPrice() * updateRentalRequest.getRentedForDays();
		rental.setTotalPrice(totalPrice);

		Rental rentalUpdated = this.rentalRepository.save(rental);
		rentalUpdatedEvent.setNewCarId(rentalUpdated.getCarId());
		rentalUpdatedEvent.setMessage("Rental Updated");
		rentalProducer.sendMessage(rentalUpdatedEvent);

		UpdateRentalResponse updateRentalResponse = this.modelMapperService.forResponse().map(rental,
				UpdateRentalResponse.class);

		return new SuccessDataResult<UpdateRentalResponse>(updateRentalResponse, Messages.RentalUpdated);
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
	public DataResult<List<GetAllRentalResponse>> getAll() {
		List<Rental> rentals = this.rentalRepository.findAll();
		List<GetAllRentalResponse> getAllRentalResponses = rentals.stream()
				.map(rental -> this.modelMapperService.forResponse().map(rental, GetAllRentalResponse.class))
				.collect(Collectors.toList());
		return new SuccessDataResult<List<GetAllRentalResponse>>(getAllRentalResponses, Messages.RentalListed);
	}

	@Override
	public Result delete(String id) {
		checkIfRentalExistsId(id);
		this.rentalRepository.deleteById(id);
		return new SuccessResult(Messages.RentalDeleted);
	}

	private void checkIfRentalExistsState(String carId) {// arabanın kiralanıp kiralanmadığı kontrolü
		GetAllCarResponse getCarResponse = inventoryClient.getByCarId(carId);
		if (getCarResponse.getState() != 1) {// kiralanmış arabaysa
			throw new BusinessException(Messages.CarHired);
		}
	}

	private void checkIfRentalExistsId(String id) {
		Rental rental = this.rentalRepository.findById(id).get();
		if (rental == null) {
			throw new BusinessException(Messages.RentalIdNotFound);
		}
	}

}
