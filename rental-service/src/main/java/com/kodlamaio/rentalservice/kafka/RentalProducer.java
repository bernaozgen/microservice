package com.kodlamaio.rentalservice.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import com.kodlamaio.common.events.rentals.RentalCreatedEvent;
import com.kodlamaio.common.events.rentals.RentalUpdateEvent;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class RentalProducer {// produsur consumera gönderiyor

	private static final Logger LOGGER = LoggerFactory.getLogger(RentalProducer.class);// konsola bi

	private NewTopic topic; // topik olusturduk

	private KafkaTemplate<String, RentalCreatedEvent> kafkaCreatedTemplate;// consume gidecek nesne
	private KafkaTemplate<String, RentalCreatedEvent> kafkaUpdatedTemplate;

	public void sendMessage(RentalCreatedEvent rentalCreatedEvent) {
		LOGGER.info(String.format("Rental created event => %s", rentalCreatedEvent.toString()));

		Message<RentalCreatedEvent> message = MessageBuilder.withPayload(rentalCreatedEvent)// mesaj+data gönderiyorum
				.setHeader(KafkaHeaders.TOPIC, topic.name()).build();// kullandığımız topikleri gönderiyoruz

		kafkaCreatedTemplate.send(message);
	}

	public void sendMessage(RentalUpdateEvent rentalUpdateEvent) {
		LOGGER.info(String.format("Rental created event => %s", rentalUpdateEvent.toString()));

		Message<RentalUpdateEvent> message = MessageBuilder.withPayload(rentalUpdateEvent)// mesaj+data gönderiyorum
				.setHeader(KafkaHeaders.TOPIC, topic.name()).build();// kullandığımız topikleri gönderiyoruz

		kafkaUpdatedTemplate.send(message);
	}

}