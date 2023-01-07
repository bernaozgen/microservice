package com.kodlamaio.invoiceservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.kodlamaio.invoiceservice.business.responses.GetCarResponse;

import feign.Headers;

@FeignClient(value="inventory-service",url="http://localhost:9011/")
public interface CarServiceClient {
	@RequestMapping(method=RequestMethod.GET , value = "stock/api/cars/{carId}")
	@Headers(value="Content-Type:application/json")
	 GetCarResponse getByCarId(@PathVariable String carId);
}
