package com.kodlamaio.invantoryServer.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.kodlamaio.common.events.rentals.RentalCreatedEvent;
import com.kodlamaio.common.events.rentals.RentalUpdateEvent;
import com.kodlamaio.invantoryServer.business.abstracts.CarService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class RentalConsumer {
	private CarService carService;
	private static final Logger LOGGER = LoggerFactory.getLogger(RentalConsumer.class);

	@KafkaListener(topics = "rental-created", groupId = "rental-create")
	public void consume(RentalCreatedEvent event) {
		LOGGER.info(String.format("Order event received in stock service => %s", event.toString()));
		carService.updateCarState(event.getCarId(),3);
	
		// save the order event into the database
	}

	@KafkaListener(topics = "rental-updated", groupId = "update")
	public void consume(RentalUpdateEvent event) {
		LOGGER.info(String.format("Order event received in stock service => %s", event.toString()));
		carService.updateCarState(event.getCarOldId(),1);
		carService.updateCarState(event.getCarNewId(),3);

		// save the order event into the database
	}

}
