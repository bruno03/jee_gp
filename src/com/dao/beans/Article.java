package com.dao.beans;

public class Article {

    private Long id;
    private String description;
    private Double unitAmount;
    private Long categoryId;

    private Category category;

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

    public Double getUnitAmount() {
	return unitAmount;
    }

    public void setUnitAmount(Double unitAmount) {
	this.unitAmount = unitAmount;
    }

    public Long getCategoryId() {
	return categoryId;
    }

    public void setCategoryId(Long categoryId) {
	this.categoryId = categoryId;
    }

    public Category getCategory() {
	return category;
    }

    public void setCategory(Category category) {
	this.category = category;
    }

}
