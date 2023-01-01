package com.kodlamaio.invoicesercive.api.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kodlamaio.common.utilities.results.DataResult;
import com.kodlamaio.invoicesercive.business.abstracts.InvoiceService;
import com.kodlamaio.invoicesercive.business.requests.CreateInvoiceRequest;
import com.kodlamaio.invoicesercive.business.responses.CreateInvoiceResponse;

@RestController
@RequestMapping("/api/invoces")
public class InvoiceController {
	private InvoiceService invoiceService;

	@PostMapping
	public ResponseEntity<?> add(CreateInvoiceRequest createInvoiceRequest) {
		DataResult<CreateInvoiceResponse> result=this.invoiceService.add(createInvoiceRequest);
		if(result.isSuccess()) {
			return ResponseEntity.ok(result);
		}
		return ResponseEntity.badRequest().body(result);
	}

}
