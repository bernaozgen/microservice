package com.kodlamaio.inventoryService	.dataAccess;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kodlamaio.inventoryService.entites.Brand;;

public interface BrandRepository extends JpaRepository<Brand, String> {
	Brand findByName(String name);


}
