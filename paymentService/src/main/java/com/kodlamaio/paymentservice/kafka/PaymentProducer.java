package com.kodlamaio.paymentservice.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import com.kodlamaio.common.events.payments.PaymentCreatedEvent;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PaymentProducer {
	private static final Logger LOGGER = LoggerFactory.getLogger(PaymentProducer.class);// konsola bi

	private NewTopic topic; // topik olusturduk

	private KafkaTemplate<String, PaymentCreatedEvent> kafkaCreatedTemplate;// consume gidecek nesne

	public void sendMessage(PaymentCreatedEvent paymentCreatedEvent) {
		LOGGER.info(String.format("Rental created event => %s", paymentCreatedEvent.toString()));

		Message<PaymentCreatedEvent> message = MessageBuilder.withPayload(paymentCreatedEvent)// mesaj+data gönderiyorum
				.setHeader(KafkaHeaders.TOPIC, topic.name()).build();// kullandığımız topikleri gönderiyoruz

		kafkaCreatedTemplate.send(message);
	}

}
