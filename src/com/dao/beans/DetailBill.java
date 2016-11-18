package com.dao.beans;

public class DetailBill {

    private Long id;
    private String description;
    private Integer quantity;
    private Double unitAmount;
    private Double finalAount;
    private Long billId;

    private Bill bill;

    public Long getId() {
	return id;
    }

    public void setId(Long id) {
	this.id = id;
    }

    public String getDescription() {
	return description;
    }

    public void setDescription(String description) {
	this.description = description;
    }

    public Integer getQuantity() {
	return quantity;
    }

    public void setQuantity(Integer quantity) {
	this.quantity = quantity;
    }

    public Double getUnitAmount() {
	return unitAmount;
    }

    public void setUnitAmount(Double unitAmount) {
	this.unitAmount = unitAmount;
    }

    public Double getFinalAount() {
	return finalAount;
    }

    public void setFinalAount(Double finalAount) {
	this.finalAount = finalAount;
    }

    public Long getBillId() {
	return billId;
    }

    public void setBillId(Long billId) {
	this.billId = billId;
    }

    public Bill getBill() {
	return bill;
    }

    public void setBill(Bill bill) {
	this.bill = bill;
    }

}
