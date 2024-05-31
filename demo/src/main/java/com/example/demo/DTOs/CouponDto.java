package com.example.demo.DTOs;

import com.example.demo.Category;

import java.time.LocalDate;

public class CouponDto {
    private Long id;
    private String name;
    private int amount;
    private double price;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
    private Category category;
    private Long companyId;

    /**
     *
     * @param id
     * @param name
     * @param amount
     * @param price
     * @param description
     * @param startDate
     * @param endDate
     * @param category
     * @param companyId
     */
    public CouponDto(Long id, String name, int amount, double price, String description, LocalDate startDate, LocalDate endDate, Category category, Long companyId) {
        this.id = id;
        this.name = name;
        this.amount = amount;
        this.price = price;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.category = category;
        this.companyId = companyId;
    }

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

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }
}
