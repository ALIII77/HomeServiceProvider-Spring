package com.example.ProjectV2.service;

import com.example.ProjectV2.dto.Admin.HistoryServiceDto;
import com.example.ProjectV2.entity.*;
import com.example.ProjectV2.entity.enums.OrderStatus;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@org.springframework.stereotype.Service
public interface AdminService {

    Admin save(Admin admin);

    void changeAdminPassword(Admin admin, String newPassword);

    Optional<Admin> findAdminByUsername(String username);

    void deleteAdminByUsername(String username);

    void createNewService(Service service);

    SubService createNewSubService(String serviceName, SubService subService);

    List<Service> showAllServices();

    List<SubService> findAllSubServiceByService(Service service);

    void editSubService(String subServiceName, String description, double basePrice);

    void editSubServiceWithDescription(SubService subService, String description);

    void editSubServiceWithBasePrice(SubService subService, double basePrice);

    void addCustomer(Customer customer);

    void addExpert(Expert expert);

    void addExpertWithPicture(Expert expert, File file);

    void addExpertToSubService(Expert expert, String subServiceName);

    void deleteExpertFromSubService(Expert expert, String subServiceName);

    void expertConfirm(Expert expert);

    void deleteSubService(SubService subService);

    List<HistoryServiceDto> historyService(Map<String, String> predicateMap);

    List<HistoryServiceDto> totalHistoryOfService(OrderStatus orderStatus, Long expertId);

}
