package com.kodlamaio.invoiceservice.business.concretes;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.kodlamaio.common.utilities.mapping.ModelMapperService;
import com.kodlamaio.common.utilities.results.DataResult;
import com.kodlamaio.common.utilities.results.SuccessDataResult;
import com.kodlamaio.invoiceservice.business.abstracts.InvoiceService;
import com.kodlamaio.invoiceservice.business.constants.Messages;
import com.kodlamaio.invoiceservice.business.requests.CreateInvoiceRequest;
import com.kodlamaio.invoiceservice.business.responses.CreateInvoiceResponse;
import com.kodlamaio.invoiceservice.business.responses.GetAllInvoiceResponse;
import com.kodlamaio.invoiceservice.dataAccess.InvoiceRepository;
import com.kodlamaio.invoiceservice.entities.Invoice;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class InvoiceManager implements InvoiceService {
	private InvoiceRepository invoiceRepository;
	private ModelMapperService modelMapperService;
	

	public DataResult<CreateInvoiceResponse> add(CreateInvoiceRequest createInvoiceRequest) {
		//checkIfCarExistsById(createInvoiceRequest.getCarId());
		Invoice invoice = modelMapperService.forRequest().map(createInvoiceRequest, Invoice.class);
		invoice.setId(UUID.randomUUID().toString());
		invoice.setRentedDate(LocalDate.now());
		invoiceRepository.save(invoice);
		
		CreateInvoiceResponse response = this.modelMapperService.forResponse().map(invoice,
				CreateInvoiceResponse.class);
		return new SuccessDataResult<CreateInvoiceResponse>(response, Messages.InvoiceCreated);

	}

//	@Override
//	public Result delete(String id) {
//		//checkIfInvoiceExistsById(id);
//		this.invoiceRepository.deleteById(id);
//		return new SuccessResult(Messages.InvoiceDeleted);
//	}

//	@Override
//	public DataResult<UpdateInvoiceResponse> update(UpdateInvoiceRequest invoiceRequest) {
//		//(invoiceRequest.getId());
//	//	checkIfCarExistsById(invoiceRequest.getCarId());
//		Invoice invoice = this.modelMapperService.forRequest().map(invoiceRequest, Invoice.class);
//		invoice.setId(UUID.randomUUID().toString());
//		this.invoiceRepository.save(invoice);
//		UpdateInvoiceResponse invoiceResponse = this.modelMapperService.forResponse().map(invoice,
//				UpdateInvoiceResponse.class);
//		return new SuccessDataResult<UpdateInvoiceResponse>(invoiceResponse, Messages.InvoiceUpdated);
//	}

	@Override
	public DataResult<List<GetAllInvoiceResponse>> getAll() {
		List<Invoice> invoices = this.invoiceRepository.findAll();
		List<GetAllInvoiceResponse> allInvoiceResponses = invoices.stream()
				.map(invoice -> this.modelMapperService.forResponse().map(invoice, GetAllInvoiceResponse.class))
				.collect(Collectors.toList());
		;
		return new SuccessDataResult<List<GetAllInvoiceResponse>>(allInvoiceResponses, Messages.InvoiceListed);
	}

//	@Override
//	public DataResult<GetInvoiceResponse> getById(String id) {
//		//checkIfInvoiceExistsById(id);
//		Invoice invoice = this.invoiceRepository.findById(id).get();
//		GetInvoiceResponse getInvoiceResponse = this.modelMapperService.forResponse().map(invoice,
//				GetInvoiceResponse.class);
//		return new SuccessDataResult<GetInvoiceResponse>(getInvoiceResponse, Messages.InvoiceListed);
//	}
	
	@Override
	public void createInvoice(Invoice invoice) {
		invoice.setId(UUID.randomUUID().toString());
		invoiceRepository.save(invoice);
		
	}
	
//	private void checkIfInvoiceExistsById(String id) {
//		Invoice invoice=this.invoiceRepository.findById(id).get();
//		if(invoice==null) {
//			throw new BusinessException(Messages.InvoiceIdNotFound);
//		}		
//	}
//	private void checkIfCarExistsById(String carId) {
//		Invoice invoice=this.invoiceRepository.findById(carId).get();
//		if(invoice==null) {
//			throw new BusinessException(Messages.CarIdNotFound);
//		}
//	}
	
	

	

}
