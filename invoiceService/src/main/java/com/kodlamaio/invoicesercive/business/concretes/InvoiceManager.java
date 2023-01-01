package com.kodlamaio.invoicesercive.business.concretes;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.kodlamaio.common.events.invoice.InvoiceCreatedEvent;
import com.kodlamaio.common.utilities.exceptions.BusinessException;
import com.kodlamaio.common.utilities.mapping.ModelMapperService;
import com.kodlamaio.common.utilities.results.DataResult;
import com.kodlamaio.common.utilities.results.Result;
import com.kodlamaio.common.utilities.results.SuccessDataResult;
import com.kodlamaio.common.utilities.results.SuccessResult;
import com.kodlamaio.invoicesercive.business.abstracts.InvoiceService;
import com.kodlamaio.invoicesercive.business.constants.Messages;
import com.kodlamaio.invoicesercive.business.requests.CreateInvoiceRequest;
import com.kodlamaio.invoicesercive.business.requests.UpdateInvoiceRequest;
import com.kodlamaio.invoicesercive.business.responses.CreateInvoiceResponse;
import com.kodlamaio.invoicesercive.business.responses.GetAllInvoiceResponse;
import com.kodlamaio.invoicesercive.business.responses.GetInvoiceResponse;
import com.kodlamaio.invoicesercive.business.responses.UpdateInvoiceResponse;
import com.kodlamaio.invoicesercive.dataAccess.InvoiceRepository;
import com.kodlamaio.invoicesercive.entities.Invoice;
import com.kodlamaio.invoicesercive.kafka.InvoiceProducer;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class InvoiceManager implements InvoiceService {
	private InvoiceRepository invoiceRepository;
	private ModelMapperService modelMapperService;
	private InvoiceProducer invoiceProducer;

	public DataResult<CreateInvoiceResponse> add(CreateInvoiceRequest createInvoiceRequest) {
		checkIfPaymentExistsById(createInvoiceRequest.getPaymentId());
		Invoice invoice = this.modelMapperService.forRequest().map(createInvoiceRequest, Invoice.class);
		invoice.setId(UUID.randomUUID().toString());

		Invoice invoice2 =this.invoiceRepository.save(invoice);

		InvoiceCreatedEvent invoiceCreatedEvent = new InvoiceCreatedEvent();
		invoiceCreatedEvent.setPeymantId(invoice2.getPaymentId());
		invoiceCreatedEvent.setMessage("Payment created");

		this.invoiceProducer.sendMessage(invoiceCreatedEvent);

		CreateInvoiceResponse response = this.modelMapperService.forResponse().map(invoice,
				CreateInvoiceResponse.class);
		return new SuccessDataResult<CreateInvoiceResponse>(response, Messages.InvoiceCreated);

	}

	@Override
	public Result delete(String id) {
		checkIfInvoiceExistsById(id);
		this.invoiceRepository.deleteById(id);
		return new SuccessResult(Messages.InvoiceDeleted);
	}

	@Override
	public DataResult<UpdateInvoiceResponse> update(UpdateInvoiceRequest invoiceRequest) {
		checkIfInvoiceExistsById(invoiceRequest.getId());
		checkIfPaymentExistsById(invoiceRequest.getPaymentId());
		Invoice invoice = this.modelMapperService.forRequest().map(invoiceRequest, Invoice.class);
		invoice.setId(UUID.randomUUID().toString());
		this.invoiceRepository.save(invoice);
		UpdateInvoiceResponse invoiceResponse = this.modelMapperService.forResponse().map(invoice,
				UpdateInvoiceResponse.class);
		return new SuccessDataResult<UpdateInvoiceResponse>(invoiceResponse, Messages.InvoiceUpdated);
	}

	@Override
	public DataResult<List<GetAllInvoiceResponse>> getAll() {
		List<Invoice> invoices = this.invoiceRepository.findAll();
		List<GetAllInvoiceResponse> allInvoiceResponses = invoices.stream()
				.map(invoice -> this.modelMapperService.forResponse().map(invoice, GetAllInvoiceResponse.class))
				.collect(Collectors.toList());
		;
		return new SuccessDataResult<List<GetAllInvoiceResponse>>(allInvoiceResponses, Messages.InvoiceListed);
	}

	@Override
	public DataResult<GetInvoiceResponse> getById(String id) {
		checkIfInvoiceExistsById(id);
		Invoice invoice = this.invoiceRepository.findById(id).get();
		GetInvoiceResponse getInvoiceResponse = this.modelMapperService.forResponse().map(invoice,
				GetInvoiceResponse.class);
		return new SuccessDataResult<GetInvoiceResponse>(getInvoiceResponse, Messages.InvoiceListed);
	}
	
	private void checkIfInvoiceExistsById(String id) {
		Invoice invoice=this.invoiceRepository.findById(id).get();
		if(invoice==null) {
			throw new BusinessException(Messages.InvoiceIdNotFound);
		}		
	}
	private void checkIfPaymentExistsById(String paymentId) {
		Invoice invoice=this.invoiceRepository.findById(paymentId).get();
		if(invoice==null) {
			throw new BusinessException(Messages.PaymentIdNotFound);
		}
	}
	
	

	

}
