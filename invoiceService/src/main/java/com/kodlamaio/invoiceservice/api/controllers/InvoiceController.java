package com.kodlamaio.invoiceservice.api.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kodlamaio.common.utilities.results.DataResult;
import com.kodlamaio.common.utilities.results.Result;
import com.kodlamaio.invoiceservice.business.abstracts.InvoiceService;
import com.kodlamaio.invoiceservice.business.requests.CreateInvoiceRequest;
import com.kodlamaio.invoiceservice.business.requests.UpdateInvoiceRequest;
import com.kodlamaio.invoiceservice.business.responses.CreateInvoiceResponse;
import com.kodlamaio.invoiceservice.business.responses.GetAllInvoiceResponse;
import com.kodlamaio.invoiceservice.business.responses.GetInvoiceResponse;
import com.kodlamaio.invoiceservice.business.responses.UpdateInvoiceResponse;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/invoices")
@AllArgsConstructor
public class InvoiceController {
	private InvoiceService invoiceService;

	@PostMapping
	public ResponseEntity<?> add(@RequestBody @Valid CreateInvoiceRequest createInvoiceRequest) {
		DataResult<CreateInvoiceResponse> result = this.invoiceService.add(createInvoiceRequest);
		if (result.isSuccess()) {
			return ResponseEntity.ok(result);
		}
		return ResponseEntity.badRequest().body(result);
	}

	@GetMapping()
	public ResponseEntity<?> getAll() {
		DataResult<List<GetAllInvoiceResponse>> result = this.invoiceService.getAll();
		if(result.isSuccess()) {
			return ResponseEntity.ok(result);
		}
		return ResponseEntity.badRequest().body(result);
		
	}
    @DeleteMapping
    public ResponseEntity<?> delete(@PathVariable @Valid String id) {
    	Result result=this.invoiceService.delete(id);
    	if(result.isSuccess()) {
    		return ResponseEntity.ok(result);
    	}
    	return ResponseEntity.badRequest().body(result);
    }
    
    @PutMapping
    public ResponseEntity<?> update (@RequestBody @Valid UpdateInvoiceRequest invoiceRequest){
    	DataResult<UpdateInvoiceResponse> result=this.invoiceService.update(invoiceRequest);
    	if(result.isSuccess()) {
    		return ResponseEntity.ok(result);
    	}
    	return ResponseEntity.badRequest().body(result);
    	
    }
    
    @GetMapping("/id")
    public ResponseEntity<?> getById(@PathVariable @Valid String id){
    	DataResult<GetInvoiceResponse> result=this.invoiceService.getById(id);
    	if(result.isSuccess()) {
    		return ResponseEntity.ok(result);
    	}
    	return ResponseEntity.badRequest().body(result);
    }
    
}
