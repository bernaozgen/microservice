package com.kodlamaio.common.events.invoice;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceCreatedEvent {
	private String message;
	private String peymantId;
}
