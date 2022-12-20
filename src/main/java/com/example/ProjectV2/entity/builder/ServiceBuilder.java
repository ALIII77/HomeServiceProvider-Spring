package com.example.ProjectV2.entity.builder;


import com.example.ProjectV2.entity.Service;
import com.example.ProjectV2.entity.SubService;

import java.util.Set;

public  class ServiceBuilder {
    private String name;
    private Set<SubService> subServices;

    ServiceBuilder() {
    }
    public static ServiceBuilder builder() {
        return new ServiceBuilder();
    }

    public ServiceBuilder name(String name) {
        this.name = name;
        return this;
    }

    public ServiceBuilder subServices(Set<SubService> subServices) {
        this.subServices = subServices;
        return this;
    }

    public Service build() {
        return new Service(name, subServices);
    }

    public String toString() {
        return "Service.ServiceBuilder(name=" + this.name + ", subServices=" + this.subServices + ")";
    }
}