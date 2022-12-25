package com.example.ProjectV2.controller;

import com.example.ProjectV2.entity.*;
import com.example.ProjectV2.entity.builder.SubServiceBuilder;
import com.example.ProjectV2.service.*;
import lombok.*;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    public final AdminService adminService;
    public final ServiceService serviceService;
    public final SubServiceService subServiceService;
    public final ExpertService expertService;
    public final CustomerService customerService;


    @PostMapping("save-admin")
    public void save(@RequestBody Admin admin) {
        adminService.save(admin);
    }


    @PostMapping("save-expert")
    public void save(@RequestBody Expert expert) {
        expertService.save(expert);
    }


    @PostMapping("save-expert-picture")
    public void saveExpertWithPicture(@RequestBody SaveExpertWithPictureDTO saveExpertWithPictureDTO) {
        if (expertService.findExpertById(saveExpertWithPictureDTO.getExpertId()).isEmpty()) {                   //in okeye?
            System.out.println("Not found expert to add avatar picture!");
        }
        expertService.findExpertById(saveExpertWithPictureDTO.getExpert().getId());
        expertService.saveExpertWithPicture(saveExpertWithPictureDTO.getExpert(), saveExpertWithPictureDTO.getPictureFile());
    }


    @PostMapping("save-customer")
    public void save(@RequestBody Customer customer) {
        customerService.save(customer);
    }


    @PutMapping("change-password-admin")
    public void changePassword(@RequestBody ChangePasswordDTO changePasswordDTO) {
        adminService.changeAdminPassword(changePasswordDTO.getAdmin(), changePasswordDTO.getNewPassword());
    }


    @PostMapping("save-service")
    public void createNewService(@RequestBody Service service) {
        serviceService.save(service);
    }


    @PostMapping("save-sub-service")
    public void createNewSubService(@RequestBody SubService subService) {
        subServiceService.save(subService);
    }


    @PutMapping("edit-sub-service")
    public void editSubService(@RequestBody EditSubServiceDTO editSubServiceDTO) {
        subServiceService.editSubService
                (editSubServiceDTO.getSubService().getName(), editSubServiceDTO.getDescription(), editSubServiceDTO.getBasePrice());
    }


    @PutMapping("edit-sub-service-with-description")
    public void editSubServiceWithDescription(@RequestBody EditSubServiceDTO editSubServiceDTO) {
        subServiceService.editSubServiceWithDescription
                (editSubServiceDTO.getSubService(), editSubServiceDTO.getDescription());
    }


    @PutMapping("edit-sub-service-with-base-price")
    public void editSubServiceWithBasePrice(@RequestBody EditSubServiceDTO editSubServiceDTO) {
        subServiceService.editSubServiceWithBasePrice
                (editSubServiceDTO.getSubService(), editSubServiceDTO.getBasePrice());
    }

    @DeleteMapping("delete-sub-service/{subServiceName}")                                     //check
    public void deleteSubServiceWithSubServiceName(@PathVariable String subServiceName) {
        SubService findSuService = new SubServiceBuilder().name(subServiceName).build();
        subServiceService.deleteSubService(findSuService);
    }


    @PutMapping("add-expert-to-sub-service")
    public void addExpertToSubService(@RequestBody ExpertSubServiceDTO addExpertToSubService) {
        expertService.addExpertToSubService(addExpertToSubService.getExpert(), addExpertToSubService.getSubServiceName());
    }


    @DeleteMapping("delete-expert-from-sub-service")
    public void deleteExpertFromSubService(@RequestBody ExpertSubServiceDTO expertSubService) {
        expertService.deleteExpertFromSubService(expertSubService.getExpert(), expertSubService.getSubServiceName());
    }


    @PutMapping("expert-confirm")
    public void expertConfirm(@RequestBody String expertUsername) {
        Expert findExpert = new Expert();
        findExpert.setUsername(expertUsername);
        expertService.expertConfirm(findExpert);
    }


    @DeleteMapping("delete-sub-service")          /* ?subServiceId=3;*/
    public void deleteSubService(@RequestParam Long subServiceId) {
        SubService subService = new SubService();
        subService.setId(subServiceId);
        subServiceService.deleteSubService(subService);
    }













    /*id example*/
//    @PutMapping("{id}")
//    public void editSubServiceWithBasePrice(@PathVariable Long id editSubServiceWithBasePriceDTO) {
//        subServiceService.editSubServiceWithBasePrice
//                (editSubServiceWithBasePriceDTO.subService, editSubServiceWithBasePriceDTO.basePrice);
//    }


    //ADMIN DTO Class
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    static class ChangePasswordDTO {
        private Admin admin;
        private String newPassword;
    }


    //SUB SERVICE DTO Class
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    static class EditSubServiceDTO {
        private SubService subService;
        private String description;
        private double basePrice;
    }


    //EXPERT DTO Class
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    static class SaveExpertWithPictureDTO {
        private Expert expert;
        private File pictureFile;

        private Long expertId;
    }


    //CHANGE PASSWORD EXPERT
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    static class ChangePasswordExpertDTO {
        private Expert expert;
        private String newPassword;
    }


    //ADD EXPERT TO SUB SERVICE DTO CLASS
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    static class ExpertSubServiceDTO {
        private Expert expert;
        private String subServiceName;
    }


}
