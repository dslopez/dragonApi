package com.odigeo.dragon.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "user")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;
	
	@Column(name = "name", length = 100)
	private String name;
	
	@Column(name = "gender", length = 1)
	private String gender;
	
	@Column(name = "address", length = 100)
	private String address;
	
	@Column(name = "birthdate",  length = 10)
	private String birthdate;
	
	@Column(name = "nacionality", length = 20)
	private String nacionality;
	
	@Column(name = "corporate", length = 1)
	private Boolean corporate;
	
	@Column(name = "role", length = 20)
	private String role;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(String birthDate) {
		this.birthdate = birthDate;
	}

	public String getNacionality() {
		return nacionality;
	}

	public void setNacionality(String nacionality) {
		this.nacionality = nacionality;
	}

	public Boolean getCorporate() {
		return corporate;
	}

	public void setCorporate(Boolean corporate) {
		this.corporate = corporate;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", gender=" + gender + ", address=" + address + ", birthdate=" + birthdate
				+ ", nacionality=" + nacionality + ", corporate=" + corporate + ", role=" + role + "]";
	}
}
