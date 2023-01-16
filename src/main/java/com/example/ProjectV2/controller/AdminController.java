package com.example.ProjectV2.controller;

import com.example.ProjectV2.dto.Admin.*;

import com.example.ProjectV2.entity.*;
import com.example.ProjectV2.service.*;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;
    private final ServiceService serviceService;
    private final SubServiceService subServiceService;
    private final ExpertService expertService;
    private final CustomerService customerService;
    private final ModelMapper modelMapper;


    @PostMapping("/save-admin")
    public void save(@RequestBody AdminDto adminDto) {
        adminService.save(adminDto.getAdmin());
    }


    @PutMapping("change-password-admin")
    public void changePassword(@RequestBody AdminChangePasswordDto adminChangePasswordDto, Authentication authentication) {
        Admin admin = (Admin) authentication.getPrincipal();
        adminService.changeAdminPassword(admin, adminChangePasswordDto.getNewPassword());
    }


    @PostMapping("save-service")
    public void createNewService(@RequestBody ServiceSaveDto serviceSaveDto) {
        serviceService.save(serviceSaveDto.getService());
    }


    @PostMapping("save-sub-service")
    public void createNewSubService(@RequestBody SubServiceSaveDto subServiceSaveDto) {
        subServiceService.save(subServiceSaveDto.getServiceName(), subServiceSaveDto.getSubService());
    }


    @PutMapping("edit-sub-service")
    public void editSubService(@RequestBody EditSubServiceByAdminDto editSubServiceDTO) {
        subServiceService.editSubService
                (editSubServiceDTO.getSubServiceName(), editSubServiceDTO.getDescription(), editSubServiceDTO.getBasePrice());
    }


    @PutMapping("edit-sub-service-with-description")
    public void editSubServiceWithDescription(@RequestBody EditDescriptionSubServiceByAdminDto
                                                      editDescriptionSubServiceByAdminDto) {
        subServiceService.editSubServiceWithDescription
                (editDescriptionSubServiceByAdminDto.getSubService(), editDescriptionSubServiceByAdminDto.getDescription());
    }


    @PutMapping("edit-sub-service-with-base-price")
    public void editSubServiceWithBasePrice(@RequestBody EditBasePriceSubServiceByAdminDto editBasePriceSubServiceByAdminDto) {
        subServiceService.editSubServiceWithBasePrice
                (editBasePriceSubServiceByAdminDto.getSubService(), editBasePriceSubServiceByAdminDto.getBasePrice());
    }


    @PutMapping("add-expert-to-sub-service")
    public void addExpertToSubService(@RequestBody AddExpertToSubServiceDto addExpertToSubService) {
        expertService.addExpertToSubService(addExpertToSubService.getExpert(), addExpertToSubService.getSubServiceName());
    }


    @DeleteMapping("delete-sub-service/{subServiceName}")
    public void deleteSubServiceWithSubServiceName(@PathVariable String subServiceName) {
        SubService findSuService = subServiceService.findSubServiceByName(subServiceName).get();
        subServiceService.deleteSubService(findSuService);
    }


    @PutMapping("expert-confirm")
    public void expertConfirm(@RequestBody ExpertConfirmByAdminDto expertConfirmByAdminDto) {
        Expert findExpert = new Expert();
        findExpert.setUsername(expertConfirmByAdminDto.getExpertUsername());
        expertService.expertConfirm(findExpert);
    }


    @DeleteMapping("delete-expert-form-sub-service")
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
        expertDto.setScore(expert.getScore());

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
        return expertDtoList(expertList);
    }


    @GetMapping("show-all-customer")
    public List<CustomerDto> showAllCustomer() {
        List<Customer> customerList = customerService.showAllCustomer();
        return customerDtoList(customerList);
    }


    @DeleteMapping("delete-customer/{customerUsername}")
    public void deleteExpert(@PathVariable String customerUsername) {
        customerService.deleteCustomer(customerUsername);
    }


    @GetMapping("find-customer-by-filter")
    public List<CustomerDto> searchCustomer(@RequestParam Map<String, String> predicateMap) {
        List<Customer> customerList = customerService.searchCustomer(predicateMap);
        return customerDtoList(customerList);
    }


    @GetMapping("find-expert-by-filter")
    public List<ExpertDto> searchExpert(@RequestParam Map<String, String> predicateMap) {
        List<Expert> expertList = expertService.searchExpert(predicateMap);
        return expertDtoList(expertList);
    }


    private List<ExpertDto> expertDtoList(List<Expert> expertList) {
        List<ExpertDto> expertDtoList = new ArrayList<>();
        for (Expert o : expertList) {
            expertDtoList.add(modelMapper.map(o, ExpertDto.class));
        }
        return expertDtoList;

    }


    @GetMapping("total-history-of-service")
    public List<HistoryServiceDto> totalHistoryOfService(@RequestBody OrderStatusExpertIdDto orderStatusExpertIdDto) {
        return adminService.totalHistoryOfService(orderStatusExpertIdDto.getOrderStatus(), orderStatusExpertIdDto.getExpertId());
    }


    @GetMapping("history-service")
    public List<HistoryServiceDto> findAllOrderByOrderStatusAndExpertId(@RequestParam Map<String, String> predicateMap) {
        return adminService.historyService(predicateMap);
    }


    private List<CustomerDto> customerDtoList(List<Customer> customerList) {
        List<CustomerDto> customerDtoList = new ArrayList<>();
        for (Customer o : customerList) {
            customerDtoList.add(modelMapper.map(o, CustomerDto.class));
        }
        return customerDtoList;

    }


    @GetMapping("customer-report")
    public List<CustomerDto> customerReport(@RequestParam Map<String, String> mapPredicate) {
        List<Customer> customerList = customerService.customerReport(mapPredicate);
        List<CustomerDto> customerDtoList = customerDtoList(customerList);
        return customerDtoList;
    }


    @GetMapping("expert-report")
    public List<ExpertDto> expertReport(@RequestParam Map<String, String> mapPredicate) {
        List<Expert> expertList = expertService.expertReport(mapPredicate);
        List<ExpertDto> expertDtoList = expertDtoList(expertList);
        return expertDtoList;
    }


}
