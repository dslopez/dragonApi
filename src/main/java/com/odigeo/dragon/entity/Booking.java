package com.odigeo.dragon.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "booking")
public class Booking {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Long userid;
	private Long flightid;

	public Booking() {
		super();
	}

	public Booking(Long userid, Long flightid) {
		super();
		this.userid = userid;
		this.flightid = flightid;
	}


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getUserid() {
		return userid;
	}

	public void setUserid(Long userid) {
		this.userid = userid;
	}

	public Long getFlightid() {
		return flightid;
	}

	public void setFlightid(Long flightid) {
		this.flightid = flightid;
	}
}
