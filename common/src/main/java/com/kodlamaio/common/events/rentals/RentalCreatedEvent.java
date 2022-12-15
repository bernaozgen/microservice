package com.kodlamaio.common.events.rentals;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RentalCreatedEvent {
	private String message;
	private String carId;
}
///aldığım aracın id sini gönderiyoruz state degişmek için kullanıyoruz. 
//EVENT olayı burası
