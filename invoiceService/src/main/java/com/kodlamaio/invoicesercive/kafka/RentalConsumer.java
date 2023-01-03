package com.kodlamaio.invoicesercive.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;

import com.kodlamaio.common.events.rentals.InvoiceCreatedEvent;
import com.kodlamaio.common.utilities.mapping.ModelMapperService;
import com.kodlamaio.invoicesercive.business.abstracts.InvoiceService;
import com.kodlamaio.invoicesercive.business.responses.GetAllCarResponse;
import com.kodlamaio.invoicesercive.client.CarServiceClient;
import com.kodlamaio.invoicesercive.entities.Invoice;

public class RentalConsumer {
	private InvoiceService invoiceService;
	private ModelMapperService modelMapperService;
	private CarServiceClient carServiceClient;
	
	
	private static final Logger LOGGER = LoggerFactory.getLogger(RentalConsumer.class);

	@KafkaListener(topics = "invoice_created", groupId = "invoice_created")
	public void consume(InvoiceCreatedEvent event) {
		Invoice invoice =this.modelMapperService.forRequest().map(event, Invoice.class);
		GetAllCarResponse response=carServiceClient.getByCarId(event.getCarId());
		invoice.setDailyPrice(event.getDailyPrice());
		invoice.setTotalPrice(event.getTotalPrice());
		invoice.setBrandName(response.getBrandName());
		invoice.setModelName(response.getModelName());
		invoice.setModelYear(response.getModelYear());
		invoiceService.createInvoice(invoice);
		
		
		LOGGER.info(String.format("Invoice created consume : {} ", event.toString()));
	
		
	}

}
