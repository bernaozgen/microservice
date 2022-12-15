package com.kodlamaio.invoicesercive.business.abstracts;

import java.util.List;

import com.kodlamaio.invoicesercive.business.requests.CreateInvoiceRequest;
import com.kodlamaio.invoicesercive.business.requests.UpdateInvoiceRequest;
import com.kodlamaio.invoicesercive.business.responses.CreateInvoiceResponse;
import com.kodlamaio.invoicesercive.business.responses.GetAllInvoiceResponse;
import com.kodlamaio.invoicesercive.business.responses.UpdateInvoiceResponse;

public interface InvoiceService {
	CreateInvoiceResponse add(CreateInvoiceRequest createInvoiceRequest);

	void delete(String id);

	UpdateInvoiceResponse update(UpdateInvoiceRequest invoiceRequest);

	List<GetAllInvoiceResponse> getAll();

}
