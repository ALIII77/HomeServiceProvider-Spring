package com.example.ProjectV2.controller;

import com.example.ProjectV2.entity.Admin;
import com.example.ProjectV2.service.AdminService;
import lombok.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
public class AdminController {

    public final AdminService adminService;

    //SAVE ADMIN
    @PostMapping("/save")
    public void save(@RequestBody Admin admin) {          //@RequestBody for convert JSON to Object
        adminService.save(admin);
    }

    @PutMapping("/changepass")
    public void changePassword(@RequestBody changePasswordDTO changePasswordDTO){
        adminService.changeAdminPassword(changePasswordDTO.admin,changePasswordDTO.newPassword);
    }




    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    static class changePasswordDTO{
        private Admin admin;
        private String newPassword;
    }



}
