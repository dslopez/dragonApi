package com.odigeo.dragon.rest;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.odigeo.dragon.entity.Flight;
import com.odigeo.dragon.service.FlightService;

@Path("/flights")
@Component
public class FlightRestResource {

	@Autowired
	FlightService flightService;
	
	@GET
    @Produces(MediaType.APPLICATION_JSON)
	@Path("/origin/{origin}/destination/{destination}/order/{order}/results/{results}")
	/**
	 * Search flights 
	 * 
	 * @param origin
	 * @param destination
	 * @param order price/duration
	 * @param results -1:ALL
	 * @return
	 */
    public Response searchFlights(@PathParam(value="origin") String origin,
    		@PathParam(value="destination") String destination,
    		@PathParam(value="order") String order,
    		@PathParam(value="results") Integer results) {
        System.out.println("searchFlights called");
		List<Flight> flights = flightService.searchFlights(origin, destination, order.equals("price")?0:1, results);
		return Response.status(200).entity(flights).build();
    }
}
