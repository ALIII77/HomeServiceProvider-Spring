package com.example.ProjectV2.entity.builder;


import com.example.ProjectV2.entity.*;
import com.example.ProjectV2.enums.OrderStatus;

import java.time.LocalDateTime;
import java.util.Set;

public class OrderBuilder {
    private Customer customer;
    private Expert expert;
    private String jobDescription;
    private LocalDateTime executionDate;
    private String address;
    private double proposedPrice;
    private OrderStatus orderStatus;
    private Set<Offer> offerSet;
    private SubService subService;
    private Comment comment;

    public OrderBuilder() {
    }
    public static OrderBuilder builder() {
        return new OrderBuilder();
    }

    public OrderBuilder customer(Customer customer) {
        this.customer = customer;
        return this;
    }

    public OrderBuilder expert(Expert expert) {
        this.expert = expert;
        return this;
    }

    public OrderBuilder jobDescription(String jobDescription) {
        this.jobDescription = jobDescription;
        return this;
    }

    public OrderBuilder executionDate(LocalDateTime executionDate) {
        this.executionDate = executionDate;
        return this;
    }

    public OrderBuilder address(String address) {
        this.address = address;
        return this;
    }

    public OrderBuilder proposedPrice(double proposedPrice) {
        this.proposedPrice = proposedPrice;
        return this;
    }

    public OrderBuilder orderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
        return this;
    }

    public OrderBuilder offerSet(Set<Offer> offerSet) {
        this.offerSet = offerSet;
        return this;
    }

    public OrderBuilder subService(SubService subService) {
        this.subService = subService;
        return this;
    }

    public OrderBuilder comment(Comment comment) {
        this.comment = comment;
        return this;
    }

    public Order build() {
        return new Order(customer, expert, jobDescription, executionDate, address, proposedPrice, orderStatus, offerSet, subService, comment);
    }

    public String toString() {
        return "Order.OrderBuilder(customer=" + this.customer + ", expert=" + this.expert + ", jobDescription=" + this.jobDescription + ", executionDate=" + this.executionDate + ", address=" + this.address + ", proposedPrice=" + this.proposedPrice + ", orderStatus=" + this.orderStatus + ", offerSet=" + this.offerSet + ", subService=" + this.subService + ", comment=" + this.comment + ")";
    }
}