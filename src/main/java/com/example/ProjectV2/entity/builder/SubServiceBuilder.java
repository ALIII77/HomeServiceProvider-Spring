package com.example.ProjectV2.entity.builder;


import com.example.ProjectV2.entity.Expert;
import com.example.ProjectV2.entity.Order;
import com.example.ProjectV2.entity.Service;
import com.example.ProjectV2.entity.SubService;

import java.util.Set;



public  class SubServiceBuilder {
    private String name;
    private Service service;
    private String description;
    private double basePrice;
    private Set<Expert> expertSet;
    private Set<Order> orderSet;

    public SubServiceBuilder() {
    }

    public static SubServiceBuilder builder() {
        return new SubServiceBuilder();
    }

    public SubServiceBuilder name(String name) {
        this.name = name;
        return this;
    }

    public SubServiceBuilder service(Service service) {
        this.service = service;
        return this;
    }

    public SubServiceBuilder description(String description) {
        this.description = description;
        return this;
    }

    public SubServiceBuilder basePrice(double basePrice) {
        this.basePrice = basePrice;
        return this;
    }

    public SubServiceBuilder expertSet(Set<Expert> expertSet) {
        this.expertSet = expertSet;
        return this;
    }

    public SubServiceBuilder orderSet(Set<Order> orderSet) {
        this.orderSet = orderSet;
        return this;
    }

    public SubService build() {
        return new SubService(name, service, description, basePrice, expertSet, orderSet);
    }

    public String toString() {
        return "SubService.SubServiceBuilder(name=" + this.name + ", service=" + this.service + ", description=" + this.description + ", basePrice=" + this.basePrice + ", expertSet=" + this.expertSet + ", orderSet=" + this.orderSet + ")";
    }
}
