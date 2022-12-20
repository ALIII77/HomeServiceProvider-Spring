package com.example.ProjectV2.service;

import com.example.ProjectV2.entity.Expert;
import com.example.ProjectV2.entity.Service;
import com.example.ProjectV2.entity.SubService;
import java.util.List;
import java.util.Optional;

public interface SubServiceService {

    SubService save(String serviceName, SubService subService);

    List<SubService> findAllSubServicesByService( Service service);

    boolean findSubServiceInService(String serviceName, String subServiceName);

    void editSubService(String subServiceName,String description, double basePrice);

    void editSubServiceWithDescription(SubService subService,String description);

    void editSubServiceWithBasePrice(SubService subService,double basePrice);

    Optional<SubService>findSubServiceByName(String subServiceName);

    Optional<SubService>findSubServiceById(Long id);

    void deleteSubService(SubService subService);



}
