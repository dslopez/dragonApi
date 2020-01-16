package com.odigeo.dragon.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.odigeo.dragon.service.FlightService;

@Component
public class ApplicationStartedListened implements ApplicationListener<ContextRefreshedEvent> {

	@Autowired
	FlightService flightService;
	
	@Override
	public void onApplicationEvent(ContextRefreshedEvent arg0) {
		System.out.println("Aplicación arrancada. Preparando datos de vuelos...");
		
		flightService.initializeFlights();
		
		System.out.println("Datos inicializados.");
	}

}
