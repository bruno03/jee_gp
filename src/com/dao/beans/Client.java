package com.dao.beans;

import java.util.List;

public class Client {

    private Long id;
    private String firstname;
    private String lastname;
    private String addresse;
    private String cp;
    private String city;

    private List<Car> cars;

    public Long getId() {
	return id;
    }

    public void setId(Long id) {
	this.id = id;
    }

    public String getFullName() {
	return firstname + " " + lastname;
    }

    public String getFirstname() {
	return firstname;
    }

    public void setFirstname(String firstname) {
	this.firstname = firstname;
    }

    public String getLastname() {
	return lastname;
    }

    public void setLastname(String lastname) {
	this.lastname = lastname;
    }

    public String getAddresse() {
	return addresse;
    }

    public void setAddresse(String addresse) {
	this.addresse = addresse;
    }

    public String getCp() {
	return cp;
    }

    public void setCp(String cp) {
	this.cp = cp;
    }

    public String getCity() {
	return city;
    }

    public void setCity(String city) {
	this.city = city;
    }

    public List<Car> getCars() {
	return cars;
    }

    public void setCars(List<Car> cars) {
	this.cars = cars;
    }

}
