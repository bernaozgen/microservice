package com.kodlamaio.rentalservice.kafka.consumers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.kodlamaio.common.events.payments.PaymentCreatedEvent;
import com.kodlamaio.rentalservice.business.abstracts.RentalService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PaymentConsumer {
	private RentalService rentalService;
	private static final Logger LOGGER = LoggerFactory.getLogger(PaymentConsumer.class);

	@KafkaListener(topics = "${spring.kafka.topic.name}", groupId = "created")
	public void consume(PaymentCreatedEvent event) {
		LOGGER.info(String.format("Order event received in stock service => %s", event.toString()));

		// save the order event into the database
	}

}
