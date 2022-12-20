package com.example.ProjectV2.service;

import com.example.ProjectV2.entity.*;
import java.io.File;
import java.util.List;
import java.util.Optional;

@org.springframework.stereotype.Service
public interface AdminService  {

    Admin save(Admin admin);

    void changeAdminPassword(Admin admin , String newPassword);

    Optional<Admin> findAdminByUsername(String username);

    void deleteAdminByUsername(String username);

    void createNewService(Service service);

    SubService createNewSubService(String serviceName, SubService subService);

    List<Service> showAllServices();

    List<SubService> findAllSubServiceByService(Service service);

    void editSubService(String subServiceName, String description, double basePrice);

    void editSubServiceWithDescription(SubService subService,String description);

    void editSubServiceWithBasePrice(SubService subService,double basePrice);

}
