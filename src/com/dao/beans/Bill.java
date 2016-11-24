package com.dao.beans;

import java.util.List;

import org.joda.time.DateTime;

public class Bill {

    private Long id;
    private Long clientId;
    private Long carId;
    private DateTime date;
    private Integer km;
    private Double amount;

    private Client client;
    private Car car;
    private List<DetailBill> details;

    public Long getId() {
	return id;
    }

    public void setId(Long id) {
	this.id = id;
    }

    public Long getClientId() {
	return clientId;
    }

    public void setClientId(Long clientId) {
	this.clientId = clientId;
    }

    public Long getCarId() {
	return carId;
    }

    public void setCarId(Long carId) {
	this.carId = carId;
    }

    public DateTime getDate() {
	return date;
    }

    public void setDate(DateTime date) {
	this.date = date;
    }

    public Integer getKm() {
	return km;
    }

    public void setKm(Integer km) {
	this.km = km;
    }

    public Double getAmount() {
	return amount;
    }

    public void setAmount(Double amount) {
	this.amount = amount;
    }

    public Client getClient() {
	return client;
    }

    public void setClient(Client client) {
	this.client = client;
    }

    public Car getCar() {
	return car;
    }

    public void setCar(Car car) {
	this.car = car;
    }

    public List<DetailBill> getDetails() {
	return details;
    }

    public void setDetails(List<DetailBill> details) {
	this.details = details;
    }

    public void calculateAmountFinal() {
	this.amount = 0.0;

	for (int i = 0; i < details.size(); i++) {
	    this.amount += details.get(i).getFinalAmount();
	}
    }

}
