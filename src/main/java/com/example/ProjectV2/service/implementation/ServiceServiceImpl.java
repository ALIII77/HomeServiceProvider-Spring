package com.example.ProjectV2.service.implementation;

import com.example.ProjectV2.entity.Service;
import com.example.ProjectV2.entity.SubService;
import com.example.ProjectV2.exception.CustomizedIllegalArgumentException;
import com.example.ProjectV2.repository.ServiceRepository;
import com.example.ProjectV2.service.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@org.springframework.stereotype.Service
public class ServiceServiceImpl implements ServiceService {

    private final ServiceRepository serviceRepository;


    @Autowired
    public ServiceServiceImpl(ServiceRepository serviceRepository) {
        this.serviceRepository = serviceRepository;
    }


    @Override
    public List<Service> findAll() {
        return serviceRepository.findAll();
    }


    @Transactional
    @Override
    public Service save(@Valid Service service) {
        try {
            if (service.getSubServices() != null) {
                for (SubService s : service.getSubServices()) {
                    s.setService(service);
                }
            }
            return serviceRepository.save(service);
        } catch (CustomizedIllegalArgumentException exception) {
            System.out.println(exception.getMessage());
        }
        return null;
    }


    @Transactional
    @Override
    public Optional<Service> findServiceByName(String name) {
        return serviceRepository.findServiceByName(name);
    }


}
