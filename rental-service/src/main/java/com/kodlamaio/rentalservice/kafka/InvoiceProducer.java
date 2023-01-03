package com.kodlamaio.rentalservice.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import com.kodlamaio.common.events.rentals.InvoiceCreatedEvent;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class InvoiceProducer {
private static final Logger LOGGER = LoggerFactory.getLogger(InvoiceProducer.class);

	
	private KafkaTemplate<String, InvoiceCreatedEvent> kafkaTemplate;
	
	public void sendMessage(InvoiceCreatedEvent invoiceCreatedEvent) {
		LOGGER.info(String.format("Invoice created event => %s", invoiceCreatedEvent.toString()));
		
		Message<InvoiceCreatedEvent> message = MessageBuilder
				.withPayload(invoiceCreatedEvent)
				.setHeader(KafkaHeaders.TOPIC, "invoice_created").build();
		
		kafkaTemplate.send(message);
	}
}
