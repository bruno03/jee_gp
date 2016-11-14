package com.dao.beans;

public class Car {

    private Long id;
    private String brand;
    private String model;
    private String immatriculation;
    private Long clientId;
    private Client client;

    public Long getId() {
	return id;
    }

    public void setId(Long id) {
	this.id = id;
    }

    public String getBrand() {
	return brand;
    }

    public void setBrand(String brand) {
	this.brand = brand;
    }

    public String getModel() {
	return model;
    }

    public void setModel(String model) {
	this.model = model;
    }

    public String getImmatriculation() {
	return immatriculation;
    }

    public void setImmatriculation(String immatriculation) {
	this.immatriculation = immatriculation;
    }

    public Long getClientId() {
	return clientId;
    }

    public void setClientId(Long clientId) {
	this.clientId = clientId;
    }

    public Client getClient() {
	return client;
    }

    public void setClient(Client client) {
	this.client = client;
    }
}
