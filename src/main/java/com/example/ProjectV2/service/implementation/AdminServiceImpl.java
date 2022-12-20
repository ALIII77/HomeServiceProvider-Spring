package com.example.ProjectV2.service.implementation;

import com.example.ProjectV2.entity.*;
import com.example.ProjectV2.exception.CustomizedIllegalArgumentException;
import com.example.ProjectV2.exception.NotFoundException;
import com.example.ProjectV2.repository.*;
import com.example.ProjectV2.service.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.List;
import java.util.Optional;


@org.springframework.stereotype.Service
public class AdminServiceImpl implements AdminService {

    private final AdminRepository adminRepository;
    private final SubServiceService subServiceService;
    private final ServiceService serviceService;
    private final CustomerService customerService;
    private final  ExpertService expertService;




    @Autowired
    public AdminServiceImpl(AdminRepository adminRepository, SubServiceService subServiceService, ServiceService serviceService,
                            CustomerService customerService, ExpertService expertService) {
        this.adminRepository = adminRepository;
        this.serviceService = serviceService;
        this.subServiceService = subServiceService;
        this.customerService = customerService;
        this.expertService = expertService;
    }


    @Transactional
    @Override
    public Admin save(@Valid Admin admin) {
        try {
            return adminRepository.save(admin);
        } catch (CustomizedIllegalArgumentException exception) {
            System.out.println(exception.getMessage());
        }
        return null;
    }

    @Transactional
    @Override
    public void changeAdminPassword(Admin admin, String newPassword) {
        Admin findAdmin = adminRepository.findAdminByUsername(admin.getUsername())
                .orElseThrow(() -> new NotFoundException("Not found admin to change password"));

        if (!admin.getPassword().equals(findAdmin.getPassword())) {
            throw new CustomizedIllegalArgumentException(" incorrect password ");
        }
        System.out.println("admin Find!");
        if ((findAdmin.getPassword().equals(newPassword))) {
            throw new CustomizedIllegalArgumentException("Enter new password that not equal with old password");
        }
        findAdmin.setPassword(newPassword);
        adminRepository.save(findAdmin);
    }

    @Override
    public Optional<Admin> findAdminByUsername(String username) {
        try {
            return adminRepository.findAdminByUsername(username);
        } catch (NotFoundException exception) {
            System.out.println(exception.getMessage());
        }
        return Optional.empty();
    }

    @Transactional
    @Override
    public void deleteAdminByUsername(String username) {
        try {
            adminRepository.deleteAdminByUsername(username);
        } catch (CustomizedIllegalArgumentException exception) {
            System.out.println(exception.getMessage());
        }
    }

    @Transactional
    @Override
    public void createNewService(@Valid Service service) {
        try {
            serviceService.save(service);
        } catch (CustomizedIllegalArgumentException exception) {
            System.out.println(exception.getMessage());
        }
    }

    @Transactional
    @Override
    public SubService createNewSubService(String serviceName, SubService subService) {
        return subServiceService.save(serviceName, subService);
    }

    @Override
    public List<Service> showAllServices() {
        return serviceService.findAll();
    }

    @Override
    public List<SubService> findAllSubServiceByService(Service service) {
        return subServiceService.findAllSubServicesByService(service);
    }

    @Transactional
    @Override
    public void editSubService(String subServiceName, String description, double basePrice) {
        subServiceService.editSubService(subServiceName, description, basePrice);
    }


    @Transactional
    @Override
    public void editSubServiceWithDescription(SubService subService, String description) {
        subServiceService.editSubServiceWithDescription(subService, description);
    }

    @Transactional
    @Override
    public void editSubServiceWithBasePrice(SubService subService, double basePrice) {
        subServiceService.editSubServiceWithBasePrice(subService, basePrice);
    }

    @Transactional
    @Override
    public void addCustomer(@Valid Customer customer) {
        try {
            customerService.save(customer);
        } catch (CustomizedIllegalArgumentException exception) {
            System.out.println(exception.getMessage());
        }
    }


    @Transactional
    @Override
    public void addExpert(@Valid Expert expert) {
        try {
            expertService.save(expert);
        } catch (CustomizedIllegalArgumentException exception) {
            System.out.println(exception.getMessage());
        }
    }

    @Transactional
    @Override
    public void addExpertWithPicture(Expert expert, File file) {
        try {
            expertService.saveExpertWithPicture(expert, file);
        } catch (CustomizedIllegalArgumentException exception) {
            System.out.println(exception.getMessage());
        }
    }


    @Transactional
    @Override
    public void addExpertToSubService(Expert expert, String subServiceName) {
        expertService.addExpertToSubService(expert, subServiceName);
    }

    @Transactional
    @Override
    public void deleteExpertFromSubService(Expert expert, String subServiceName) {
        expertService.deleteExpertFromSubService(expert, subServiceName);
    }

    @Transactional
    @Override
    public void expertConfirm(Expert expert) {
        expertService.expertConfirm(expert);
    }

    @Transactional
    @Override
    public void deleteSubService(SubService subService) {
        subServiceService.deleteSubService(subService);
    }





}