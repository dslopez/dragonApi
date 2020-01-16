package com.odigeo.dragon.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.odigeo.dragon.entity.Flight;

public interface FlightRepository extends CrudRepository<Flight, Long>{

	/**
	 * Busca vuelos por destino
	 * 
	 * @param city
	 */
	List<Flight> findByDestination(String city);

	/**
	 * Busca vuelos por origen y destino
	 * 
	 * @param origin
	 * @param destination
	 * @return
	 */
	List<Flight> findByOriginAndDestination(String origin, String destination);
}
