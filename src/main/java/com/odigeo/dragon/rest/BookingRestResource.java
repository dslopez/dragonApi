package com.odigeo.dragon.rest;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.odigeo.dragon.entity.Booking;
import com.odigeo.dragon.repository.BookingRepository;

@Path("/bookings")
@Component
public class BookingRestResource {

	@Autowired
	BookingRepository bookingRepository;
	
	@POST
    @Path("/user/{userId}/flight/{flightId}")
    public Response saveBookings(@PathParam(value = "userId") Long userId, @PathParam(value = "flightId") Long flightId) {
		System.out.println("saveBookings called: usuario id " + userId + " vuelo id " + flightId);
		bookingRepository.save(new Booking(userId, flightId));
		return Response.status(200).entity("Reserva realizada: usuario id " + userId + " vuelo id " + flightId).build();
    }
	
}
