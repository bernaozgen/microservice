package com.kodlamaio.invoicesercive.business.abstracts;

import java.util.List;

import com.kodlamaio.common.utilities.results.DataResult;
import com.kodlamaio.common.utilities.results.Result;
import com.kodlamaio.invoicesercive.business.requests.CreateInvoiceRequest;
import com.kodlamaio.invoicesercive.business.requests.UpdateInvoiceRequest;
import com.kodlamaio.invoicesercive.business.responses.CreateInvoiceResponse;
import com.kodlamaio.invoicesercive.business.responses.GetAllInvoiceResponse;
import com.kodlamaio.invoicesercive.business.responses.GetInvoiceResponse;
import com.kodlamaio.invoicesercive.business.responses.UpdateInvoiceResponse;
import com.kodlamaio.invoicesercive.entities.Invoice;

public interface InvoiceService {
	DataResult<CreateInvoiceResponse> add(CreateInvoiceRequest createInvoiceRequest);

	Result delete(String id);

	DataResult<UpdateInvoiceResponse> update(UpdateInvoiceRequest invoiceRequest);

	DataResult<List<GetAllInvoiceResponse>> getAll();

	DataResult<GetInvoiceResponse> getById(String id);

	public void createInvoice(Invoice invoice);

}
