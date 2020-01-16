package com.odigeo.dragon.service.impl;

import java.sql.Timestamp;
import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.odigeo.dragon.entity.Flight;
import com.odigeo.dragon.json.CurrencyConverterInfo;
import com.odigeo.dragon.json.discounts.Discounts;
import com.odigeo.dragon.json.flights.Flights;
import com.odigeo.dragon.json.flights.Inbound;
import com.odigeo.dragon.json.flights.Outbound;
import com.odigeo.dragon.json.flights.Result;
import com.odigeo.dragon.repository.FlightRepository;
import com.odigeo.dragon.service.FlightService;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

@Service(FlightService.SERVICE_NAME)
public class FlightServiceImpl implements FlightService {

	@Autowired
	FlightRepository flightRepository;
	
	private List<Flight> flightsToSave;

	private HashMap<String, Double> currencyMap;
	
	@Override
	public void initializeFlights() {
		
		if(flightRepository.count()==0L){
			try {

				ClientResponse response = getApiResponse("http://odigeo-testbackend.herokuapp.com/");

				Flights flights = response.getEntity(Flights.class);
				
				//Seed database with flight information
				if(flights!=null){
					flightsToSave = new ArrayList<Flight>();
					flights.getResults().forEach(result -> processResult(result));
					flightRepository.save(flightsToSave);
				}else{
					System.err.println("No hay vuelos disponibles!!!");
				}
				
			  } catch (Exception e) {

				e.printStackTrace();

			  }
		}

	}

	private ClientResponse getApiResponse(String url) {
		//https://www.mkyong.com/webservices/jax-rs/restful-java-client-with-jersey-client/
		Client client = Client.create();

		WebResource webResource = client
		   .resource(url);

		ClientResponse response = webResource.accept("application/json")
                   .get(ClientResponse.class);

		if (response.getStatus() != 200) {
		   throw new RuntimeException("Failed : HTTP error code : "
			+ response.getStatus());
		}
		
		return response;
	}

	private void processResult(Result result) {
		try {
			Inbound inbound = result.getInbound();
			Flight inFlight = new Flight(inbound.getOrigin(), inbound.getDestination(), getTimestamp(inbound.getDepartureDate(), inbound.getDepartureTime()), getTimestamp(inbound.getArrivalDate(), inbound.getArrivalTime()), result.getPrice(), result.getCurrency());
			
			Outbound outbound = result.getOutbound();
			Flight outFlight = new Flight(outbound.getOrigin(), outbound.getDestination(), getTimestamp(outbound.getDepartureDate(), outbound.getDepartureTime()), getTimestamp(outbound.getArrivalDate(), outbound.getArrivalTime()), result.getPrice(), result.getCurrency());
			
			flightsToSave.add(inFlight);
			flightsToSave.add(outFlight);
		} catch (DateTimeException e) {
			System.err.println("Resultado descartado." + e.getMessage());
		}
	}

	private Timestamp getTimestamp(String date, String time) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yyyy H:mm");
		String str = date + " " + time;
		LocalDateTime dateTime = LocalDateTime.parse(str, formatter);
		return Timestamp.valueOf(dateTime);
	}

	@Override
	public void manageOffers() {
		try {

			ClientResponse response = getApiResponse("http://odigeo-testbackend.herokuapp.com/discount");

			Discounts discounts = response.getEntity(Discounts.class);
			if(discounts!=null){
				discounts.getResults().forEach(discount -> applyDiscount(discount));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void applyDiscount(com.odigeo.dragon.json.discounts.Result discount) {
		System.out.println("Aplicando descuento para destino " + discount.getCity() + ". Dto " + discount.getDiscount() + "%");
		List<Flight> flights = flightRepository.findByDestination(discount.getCity());
		
		if(flights!=null && !flights.isEmpty()){
			System.out.println("Encontrados " + flights.size() + " vuelos");
			flights.forEach(flight -> applyOffer(flight, discount.getDiscount()));
			flightRepository.save(flights);
		}else{
			System.out.println("No hay vuelos para el destino");
		}
	}

	private void applyOffer(Flight flight, Integer discount) {
		flight.setPrice(flight.getPrice()-(flight.getPrice()*discount/100));
		flight.setOffer(true);
	}

	@Override
	public List<Flight> searchFlights(String origin, String destination, int sortType, int results) {
		List<Flight> flights = flightRepository.findByOriginAndDestination(origin, destination);
		if(flights!=null){
			flights.sort((f1, f2) -> orderFlights(f1, f2, sortType));
		}
		
		if(results>0 && results<= flights.size()){
			return flights.subList(0, results);
		}
		
		return flights;
	}

	private int orderFlights(Flight f1, Flight f2, int sortType) {
		int order = 0;
		//sortType (0: price; 1: trip duration)
		switch (sortType) {
		case 0:
			double price1 = getEuros(f1.getPrice(), f1.getCurrency());
			double price2 = getEuros(f2.getPrice(), f2.getCurrency());
			order = Double.compare(price1, price2);
			break;

		case 1:
			long duration1 = f1.getArrival().getTime() - f1.getDeparture().getTime();
			long duration2 = f2.getArrival().getTime() - f2.getDeparture().getTime();
			order = Long.compare(duration1, duration2);
			break;
		
		default:
			break;
		}
		
		return order;
	}

	private double getEuros(Double price, String currency) {
		if(currencyMap==null){
			currencyMap = new HashMap<String, Double>();
		}
		
		Double rate = currencyMap.get(currency);
		if(rate==null){
			rate = getRate(currency);
			currencyMap.put(currency, rate);
		}
		
		return price * rate;
	}

	private Double getRate(String currency) {
		String url = "http://jarvisstark.herokuapp.com/currency?from=" + currency + "&to=EUR";
		ClientResponse response = getApiResponse(url);
		CurrencyConverterInfo info = response.getEntity(CurrencyConverterInfo.class);
		return info.getExchangeRate();
	}

}
