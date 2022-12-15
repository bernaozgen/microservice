package com.kodlamaio.invoicesercive.business.concretes;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.kodlamaio.common.events.invoice.InvoiceCreatedEvent;
import com.kodlamaio.common.utilities.mapping.ModelMapperService;
import com.kodlamaio.invoicesercive.business.abstracts.InvoiceService;
import com.kodlamaio.invoicesercive.business.requests.CreateInvoiceRequest;
import com.kodlamaio.invoicesercive.business.requests.UpdateInvoiceRequest;
import com.kodlamaio.invoicesercive.business.responses.CreateInvoiceResponse;
import com.kodlamaio.invoicesercive.business.responses.GetAllInvoiceResponse;
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

	public CreateInvoiceResponse add(CreateInvoiceRequest createInvoiceRequest) {
		Invoice invoice = this.modelMapperService.forRequest().map(createInvoiceRequest, Invoice.class);
		invoice.setId(UUID.randomUUID().toString());

		this.invoiceRepository.save(invoice);

		InvoiceCreatedEvent invoiceCreatedEvent = new InvoiceCreatedEvent();
		invoiceCreatedEvent.setPeymantId(createInvoiceRequest.getPaymentId());
		invoiceCreatedEvent.setMessage("Payment created");

		this.invoiceProducer.sendMessage(invoiceCreatedEvent);

		CreateInvoiceResponse response = this.modelMapperService.forResponse().map(invoice,
				CreateInvoiceResponse.class);
		return response;

	}

	@Override
	public void delete(String id) {
		this.invoiceRepository.deleteById(id);

	}

	@Override
	public UpdateInvoiceResponse update(UpdateInvoiceRequest invoiceRequest) {
		Invoice invoice = this.modelMapperService.forRequest().map(invoiceRequest, Invoice.class);
		invoice.setId(UUID.randomUUID().toString());
		this.invoiceRepository.save(invoice);
		UpdateInvoiceResponse invoiceResponse = this.modelMapperService.forResponse().map(invoice,
				UpdateInvoiceResponse.class);
		return invoiceResponse;
	}

	@Override
	public List<GetAllInvoiceResponse> getAll() {

		return null;
	}

}
