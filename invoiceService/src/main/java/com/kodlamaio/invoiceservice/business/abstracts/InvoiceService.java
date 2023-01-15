package com.kodlamaio.invoiceservice.business.abstracts;

import java.util.List;

import com.kodlamaio.common.utilities.results.DataResult;
import com.kodlamaio.common.utilities.results.Result;
import com.kodlamaio.invoiceservice.business.requests.CreateInvoiceRequest;
import com.kodlamaio.invoiceservice.business.requests.UpdateInvoiceRequest;
import com.kodlamaio.invoiceservice.business.responses.CreateInvoiceResponse;
import com.kodlamaio.invoiceservice.business.responses.GetAllInvoiceResponse;
import com.kodlamaio.invoiceservice.business.responses.GetInvoiceResponse;
import com.kodlamaio.invoiceservice.business.responses.UpdateInvoiceResponse;
import com.kodlamaio.invoiceservice.entities.Invoice;

public interface InvoiceService {
	DataResult<CreateInvoiceResponse> add(CreateInvoiceRequest createInvoiceRequest);

	Result delete(String id);

	DataResult<UpdateInvoiceResponse> update(UpdateInvoiceRequest invoiceRequest);

	DataResult<List<GetAllInvoiceResponse>> getAll();

	DataResult<GetInvoiceResponse> getById(String id);

	public void createInvoice(Invoice invoice);

}
