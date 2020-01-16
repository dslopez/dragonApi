package com.odigeo.dragon.service;

import java.util.List;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.odigeo.dragon.entity.Flight;

public interface FlightService {

	String SERVICE_NAME = "FlightService";

	/**
	 * retrive flights from odigeo api and store them in database
	 */
	public void initializeFlights();

	/**
	 * Apply offers to flights: retrieves destinations with offers from another
	 * web service (see section 3.3) and updates the price of all the flights
	 * with this destination applying the percentage value retrieved from the
	 * service in their corresponding table and activate the flag which
	 * indicates this trip is an offer.
	 */
	@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false)
	public void manageOffers();
	
	/**
	 * Search flights
	 * 
	 * @param origin
	 * @param destination
	 * @param sortType (0: price; 1: trip duration)
	 * @param results (-1: all; n: number of results)
	 */
	public List<Flight> searchFlights(String origin, String destination, int sortType, int results);
}
