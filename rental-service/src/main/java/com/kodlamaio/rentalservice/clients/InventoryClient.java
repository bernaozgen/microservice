package com.kodlamaio.rentalservice.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.kodlamaio.rentalservice.business.responses.get.GetCarResponse;

import feign.Headers;

@FeignClient(value="inventoryService" ,url="http://localhost:9011/")
public interface InventoryClient {
	@RequestMapping(method = RequestMethod.GET, value = "stock/api/cars/{carId}") 
	@Headers(value = "Content-Type: application/json")
	GetCarResponse getByCarId(@PathVariable String carId);

}
