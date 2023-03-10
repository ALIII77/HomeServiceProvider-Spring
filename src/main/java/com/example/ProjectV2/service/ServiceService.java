package com.example.ProjectV2.service;

import com.example.ProjectV2.entity.Service;

import java.util.List;
import java.util.Optional;

public interface ServiceService {

    List<Service> findAllServices();

    Optional<Service> findServiceByName(String serviceName);

    Service save(Service service);

    boolean isNotExistsService(String serviceName);


}
