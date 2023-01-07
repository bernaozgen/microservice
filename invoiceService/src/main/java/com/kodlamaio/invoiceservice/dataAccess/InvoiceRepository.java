package com.kodlamaio.invoiceservice.dataAccess;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kodlamaio.invoiceservice.entities.Invoice;

public interface InvoiceRepository extends JpaRepository<Invoice, String> {

}
