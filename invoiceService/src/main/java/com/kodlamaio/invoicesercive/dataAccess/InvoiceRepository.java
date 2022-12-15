package com.kodlamaio.invoicesercive.dataAccess;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kodlamaio.invoicesercive.entities.Invoice;

public interface InvoiceRepository extends JpaRepository<Invoice, String> {

}
