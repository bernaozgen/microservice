package com.kodlamaio.invoicesercive.api.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kodlamaio.invoicesercive.business.abstracts.InvoiceService;
import com.kodlamaio.invoicesercive.business.requests.CreateInvoiceRequest;
import com.kodlamaio.invoicesercive.business.responses.CreateInvoiceResponse;

@RestController
@RequestMapping("/api/invoces")
public class InvoiceController {
	private InvoiceService invoiceService;

	@PostMapping
	public CreateInvoiceResponse add(CreateInvoiceRequest createInvoiceRequest) {
		return invoiceService.add(createInvoiceRequest);
	}

}
