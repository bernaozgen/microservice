package com.kodlamaio.invoiceservice.api.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kodlamaio.common.utilities.results.DataResult;
import com.kodlamaio.invoiceservice.business.abstracts.InvoiceService;
import com.kodlamaio.invoiceservice.business.requests.CreateInvoiceRequest;
import com.kodlamaio.invoiceservice.business.responses.CreateInvoiceResponse;
import com.kodlamaio.invoiceservice.business.responses.GetAllInvoiceResponse;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/invoices")
@AllArgsConstructor
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
	
	@GetMapping()
	public DataResult<List<GetAllInvoiceResponse>> getAll(){
		return this.invoiceService.getAll();
	}

}
