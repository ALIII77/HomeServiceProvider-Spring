package com.example.ProjectV2.entity.builder;


import com.example.ProjectV2.entity.Expert;
import com.example.ProjectV2.entity.Offer;
import com.example.ProjectV2.entity.Order;

import java.time.LocalDateTime;



public  class OfferBuilder {
    private Expert expert;
    private LocalDateTime offerDate;
    private double price;
    private Order order;

    public OfferBuilder() {
    }
    public static OfferBuilder builder() {
        return new OfferBuilder();
    }

    public OfferBuilder expert(Expert expert) {
        this.expert = expert;
        return this;
    }

    public OfferBuilder offerDate(LocalDateTime offerDate) {
        this.offerDate = offerDate;
        return this;
    }

    public OfferBuilder price(double price) {
        this.price = price;
        return this;
    }

    public OfferBuilder order(Order order) {
        this.order = order;
        return this;
    }

    public Offer build() {
        return new Offer(expert, offerDate, price, order);
    }

    public String toString() {
        return "Offer.OfferBuilder(expert=" + this.expert + ", offerDate=" + this.offerDate + ", price="
                + this.price + ", order=" + this.order + ")";
    }
}