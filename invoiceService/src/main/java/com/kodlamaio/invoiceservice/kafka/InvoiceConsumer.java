package com.kodlamaio.invoiceservice.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.kodlamaio.common.events.rentals.InvoiceCreatedEvent;
import com.kodlamaio.common.utilities.mapping.ModelMapperService;
import com.kodlamaio.invoiceservice.business.abstracts.InvoiceService;
import com.kodlamaio.invoiceservice.business.responses.GetCarResponse;
import com.kodlamaio.invoiceservice.client.CarServiceClient;
import com.kodlamaio.invoiceservice.entities.Invoice;

import lombok.AllArgsConstructor;
@Service
@AllArgsConstructor
@EnableKafka
public class InvoiceConsumer {
	
	private InvoiceService invoiceService;
	private ModelMapperService modelMapperService;
	private CarServiceClient carServiceClient;
	
	
	private static final Logger LOGGER = LoggerFactory.getLogger(InvoiceConsumer.class);

	@KafkaListener(topics = "invoice_created", groupId = "invoice_create")
	public void consume(InvoiceCreatedEvent event) {
		Invoice invoice =this.modelMapperService.forRequest().map(event, Invoice.class);
		GetCarResponse response=carServiceClient.getByCarId(event.getCarId());
		invoice.setDailyPrice(event.getDailyPrice());
		invoice.setTotalPrice(event.getTotalPrice());
		//invoice.setBrandName(response.getBrandName());
		//invoice.setModelName(response.getModelName());
	//	invoice.setModelYear(response.getModelYear());
		invoiceService.createInvoice(invoice);
		
		
		LOGGER.info("Invoice created consume : {} ", event.getCarId());
	
		
	}

}
