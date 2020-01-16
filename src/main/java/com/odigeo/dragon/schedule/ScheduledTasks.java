package com.odigeo.dragon.schedule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.odigeo.dragon.service.FlightService;

/**
 * Tareas programadas
 * https://spring.io/guides/gs/scheduling-tasks/#initial 
 */
@Component
public class ScheduledTasks {

	@Autowired
	FlightService flightService;
	
    @Scheduled(fixedDelay = 300000)//Cada 5 minutos
    public void manageFlightOffers() {
        System.out.println("Managing flight offers");
        flightService.manageOffers();
    }
}
