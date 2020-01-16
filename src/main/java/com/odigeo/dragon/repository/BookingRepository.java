package com.odigeo.dragon.repository;

import org.springframework.data.repository.CrudRepository;

import com.odigeo.dragon.entity.Booking;

public interface BookingRepository extends CrudRepository<Booking, Long> {

}
