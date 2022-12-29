package com.example.ProjectV2.controller;

import com.example.ProjectV2.dto.Admin.*;
import com.example.ProjectV2.entity.*;
import com.example.ProjectV2.service.*;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin")
public class AdminController {

    public final AdminService adminService;
    public final ServiceService serviceService;
    public final SubServiceService subServiceService;
    public final ExpertService expertService;
    public final CustomerService customerService;

    public AdminController(AdminService adminService, ServiceService serviceService, SubServiceService subServiceService
            , ExpertService expertService, CustomerService customerService) {
        this.adminService = adminService;
        this.serviceService = serviceService;
        this.subServiceService = subServiceService;
        this.expertService = expertService;
        this.customerService = customerService;
    }


    @PostMapping("save-admin")                                                          //ok
    public void save(@RequestBody Admin admin) {
        adminService.save(admin);
    }


    @PutMapping("change-password-admin")                                                  //ok
    public void changePassword(@RequestBody AdminChangePasswordDto adminChangePasswordDto) {
        adminService.changeAdminPassword(adminChangePasswordDto.getAdmin(), adminChangePasswordDto.getNewPassword());
    }


    @PostMapping("save-service")                                                         //ok
    public void createNewService(@RequestBody ServiceSaveDto serviceSaveDto) {
        serviceService.save(serviceSaveDto.getService());
    }


    @PostMapping("save-sub-service")                                                       //ok
    public void createNewSubService(@RequestBody SubServiceSaveDto subServiceSaveDto) {
        subServiceService.save(subServiceSaveDto.getServiceName(), subServiceSaveDto.getSubService());
    }


    @PutMapping("edit-sub-service")                                                         //ok
    public void editSubService(@RequestBody EditSubServiceByAdminDto editSubServiceDTO) {
        subServiceService.editSubService
                (editSubServiceDTO.getSubServiceName(), editSubServiceDTO.getDescription(), editSubServiceDTO.getBasePrice());
    }


    @PutMapping("edit-sub-service-with-description")                                            //ok
    public void editSubServiceWithDescription(@RequestBody EditDescriptionSubServiceByAdminDto
                                                      editDescriptionSubServiceByAdminDto) {
        subServiceService.editSubServiceWithDescription
                (editDescriptionSubServiceByAdminDto.getSubService(), editDescriptionSubServiceByAdminDto.getDescription());
    }


    @PutMapping("edit-sub-service-with-base-price")                                           //ok
    public void editSubServiceWithBasePrice(@RequestBody EditBasePriceSubServiceByAdminDto editBasePriceSubServiceByAdminDto) {
        subServiceService.editSubServiceWithBasePrice
                (editBasePriceSubServiceByAdminDto.getSubService(), editBasePriceSubServiceByAdminDto.getBasePrice());
    }


    @PutMapping("add-expert-to-sub-service")                                                              //ok
    public void addExpertToSubService(@RequestBody AddExpertToSubServiceDto addExpertToSubService) {
        expertService.addExpertToSubService(addExpertToSubService.getExpert(), addExpertToSubService.getSubServiceName());
    }


    @DeleteMapping("delete-sub-service/{subServiceName}")                                   //ok
    public void deleteSubServiceWithSubServiceName(@PathVariable String subServiceName) {
        SubService findSuService = subServiceService.findSubServiceByName(subServiceName).get();
        subServiceService.deleteSubService(findSuService);
    }


    @PutMapping("expert-confirm")                                                               //ok
    public void expertConfirm(@RequestBody ExpertConfirmByAdminDto expertConfirmByAdminDto) {
        Expert findExpert = new Expert();
        findExpert.setUsername(expertConfirmByAdminDto.getExpertUsername());
        expertService.expertConfirm(findExpert);
    }


    @DeleteMapping("delete-expert-form-sub-service")                                          //ok
    public void deleteExpertFormSubService(@RequestBody DeleteExpertFormSubServiceDto deleteExpertFormSubServiceDto) {
        Expert expert = expertService.findExpertById(deleteExpertFormSubServiceDto.getExpertId()).get();
        expertService.deleteExpertFromSubService(expert
                , deleteExpertFormSubServiceDto.getSubServiceName());
    }


    @GetMapping("find-expert-by-id/{expertId}")
    public ExpertDto findExpertById(@PathVariable Long expertId) {
        Expert expert = expertService.findExpertById(expertId).get();
        ExpertDto expertDto = new ExpertDto();
        expertDto.setFirstName(expert.getFirstName());
        expertDto.setLastName(expert.getLastName());
        expertDto.setUsername(expert.getUsername());
        expertDto.setEmail(expert.getEmail());

        return expertDto;
    }


    @GetMapping("find-customer-by-id/{customerId}")
    public CustomerDto findCustomerById(@PathVariable Long customerId) {
        Customer customer = customerService.findById(customerId).get();
        CustomerDto customerDto = new CustomerDto();
        customerDto.setFirstName(customer.getFirstName());
        customerDto.setLastName(customer.getLastName());
        customerDto.setUsername(customer.getUsername());
        customerDto.setEmail(customer.getEmail());
        return customerDto;
    }


    @GetMapping("show-all-experts")
    public List<ExpertDto> showAllExperts() {
        List<Expert> expertList = expertService.showAllExperts();
        List<ExpertDto> expertDtoList = new ArrayList<>();
        for (Expert e : expertList) {
            ExpertDto expertDto = ExpertDto.builder().
                    firstName(e.getFirstName())
                    .lastName(e.getLastName())
                    .username(e.getUsername())
                    .email(e.getEmail())
                    .score(e.getScore())
                    .expertStatus(e.getExpertStatus())
                    .build();
            expertDtoList.add(expertDto);
        }
        return expertDtoList;
    }


    @GetMapping("show-all-customer")
    public List<CustomerDto> showAllCustomer() {
        List<Customer> customerList = customerService.showAllCustomer();
        List<CustomerDto> customerDtoList = new ArrayList<>();
        for (Customer e : customerList) {
            CustomerDto customerDto = CustomerDto.builder().
                    firstName(e.getFirstName())
                    .lastName(e.getLastName())
                    .username(e.getUsername())
                    .email(e.getEmail())
                    .build();
            customerDtoList.add(customerDto);
        }
        return customerDtoList;
    }


    @DeleteMapping("delete-customer/{customerUsername}")
    public void deleteExpert(@PathVariable String customerUsername) {
        customerService.deleteCustomer(customerUsername);
    }


    @GetMapping("find-customer-by-filter")
    public List<CustomerDto> searchCustomer(Map<String, String> predicateMap) {
        List<Customer> customerList = customerService.searchCustomer(predicateMap);
        List<CustomerDto> customerDtoList = new ArrayList<>();
        for (Customer c : customerList) {
            CustomerDto customerDto = CustomerDto.builder().
                    firstName(c.getFirstName())
                    .lastName(c.getLastName())
                    .username(c.getUsername())
                    .email(c.getEmail())
                    .build();
            customerDtoList.add(customerDto);
        }
        return customerDtoList;
    }



    @GetMapping("find-expert-by-filter")
    public List<ExpertDto> searchExpert(Map<String, String> predicateMap) {
        List<Expert> expertList = expertService.searchExpert(predicateMap);
        List<ExpertDto>  expertDtoList = new ArrayList<>();
        for (Expert e : expertList) {
            ExpertDto expertDto = ExpertDto.builder().
                    firstName(e.getFirstName())
                    .lastName(e.getLastName())
                    .username(e.getUsername())
                    .email(e.getEmail())
                    .score(e.getScore())
                    .build();
            expertDtoList.add(expertDto);
        }
        return expertDtoList;
    }


}
