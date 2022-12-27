package com.example.ProjectV2.service.implementation;

import com.example.ProjectV2.entity.Expert;
import com.example.ProjectV2.entity.Service;
import com.example.ProjectV2.entity.SubService;
import com.example.ProjectV2.exception.CustomizedIllegalArgumentException;
import com.example.ProjectV2.exception.NotFoundException;
import com.example.ProjectV2.exception.NotUniqueException;
import com.example.ProjectV2.exception.PermissionDeniedException;
import com.example.ProjectV2.repository.SubServiceRepository;
import com.example.ProjectV2.service.*;
import com.example.ProjectV2.utils.QueryUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@org.springframework.stereotype.Service
public class SubServiceServiceImpl implements SubServiceService {

    private final ServiceService serviceService;
    private final SubServiceRepository subServiceRepository;


    @Autowired
    public SubServiceServiceImpl(ServiceService serviceService, SubServiceRepository subServiceRepository) {
        this.serviceService = serviceService;
        this.subServiceRepository = subServiceRepository;
    }

    @Override
    public SubService save(SubService subService) {
        return subServiceRepository.save(subService);
    }

    @Transactional
    @Override
    public SubService save(String serviceName, SubService subService) {
        SubService savedSubService = null;
        QueryUtil.checkEntity(subService);
        Optional<Service> serviceOptional = serviceService.findServiceByName(serviceName);
        if (serviceOptional.isEmpty()) {
            throw new NotFoundException("Not exists service to add sub service");
        }
        Service findService = serviceOptional.get();
        if ((findSubServiceInService(findService.getName(), subService.getName()))) {
            throw new PermissionDeniedException("this sub service is exists");
        }
        subService.setService(findService);
        savedSubService = subServiceRepository.save(subService);
        return savedSubService;
    }

    @Override
    public List<SubService> findAllSubServicesByService(Service service) {
        Optional<Service> serviceOptional = serviceService.findServiceByName(service.getName());
        if (serviceOptional.isEmpty()) {
            throw new NotFoundException("Not found this service to show all sub services");
        }
        return serviceOptional.get().getSubServices().stream().toList();
    }

    @Transactional
    @Override
    public boolean findSubServiceInService(String serviceName, String subServiceName) {
        Optional<Service> serviceOptional = serviceService.findServiceByName(serviceName);
        if (serviceOptional.isEmpty()) {
            throw new NotFoundException("Not exists " + serviceName + " service");
        }
        Service service = serviceOptional.get();
        if (service.getSubServices().stream().anyMatch(subService1 -> subService1.getName().equals(subServiceName))) {
            System.out.println("this " + subServiceName + " is exists in " + serviceName + " service");
            return true;
        } else {
            return false;
        }
    }


    @Transactional
    @Override
    public void editSubService(String subServiceName, String description, double basePrice) {
        Optional<SubService> subServiceOptional = subServiceRepository.findSubServiceByName(subServiceName);
        if (subServiceOptional.isEmpty()) {
            throw new NotFoundException("Not found sub service");
        }
        SubService findSubService = subServiceOptional.get();
        findSubService.setDescription(description);
        findSubService.setBasePrice(basePrice);
        subServiceRepository.save(findSubService);
    }


    @Transactional
    @Override
    public void editSubServiceWithDescription(SubService subService, String description) {
        Optional<SubService> subServiceOptional = subServiceRepository.findSubServiceByName(subService.getName());
        if (subServiceOptional.isEmpty()) {
            throw new NotFoundException("Not found sub service");
        }
        SubService findSubService = subServiceOptional.get();
        findSubService.setDescription(description);
        subServiceRepository.save(findSubService);
    }


    @Transactional
    @Override
    public void editSubServiceWithBasePrice(SubService subService, double basePrice) {
        Optional<SubService> subServiceOptional = subServiceRepository.findSubServiceByName(subService.getName());
        if (subServiceOptional.isEmpty()) {
            throw new NotFoundException("Not found sub service");
        }
        SubService findSubService = subServiceOptional.get();
        findSubService.setBasePrice(basePrice);
        subServiceRepository.save(findSubService);
    }


    @Override
    public Optional<SubService> findSubServiceByName(String subServiceName) {
        return subServiceRepository.findSubServiceByName(subServiceName);

    }


    @Override
    public Optional<SubService> findSubServiceById(Long id) {
        return subServiceRepository.findById(id);
    }


    @Transactional
    @Override
    public void deleteSubService(SubService subService) {
        subServiceRepository.deleteSubServiceById(subService.getId());
    }


    @Transactional
    @Override
    public void addExpert(Long subServiceId, Expert expert) {
        if (subServiceRepository.findSubServiceById(subServiceId) == null) {
            throw new NotFoundException("Not found sub service");
        }
        SubService findSubService = subServiceRepository.findSubServiceById(subServiceId);
        findSubService.addExpert(expert);
    }


    @Transactional
    @Override
    public void update(@Valid SubService subService) {
        if (subServiceRepository.findById(subService.getId()).isEmpty()) {
            throw new NotFoundException("not found sub service");
        }
        subServiceRepository.save(subService);
    }
}