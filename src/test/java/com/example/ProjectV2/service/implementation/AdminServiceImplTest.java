package com.example.ProjectV2.service.implementation;

import com.example.ProjectV2.entity.Admin;
import com.example.ProjectV2.service.AdminService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class AdminServiceImplTest {


    @Autowired
    private AdminService adminService;


    Admin admin1;
    Admin admin2;


    @BeforeEach
    public void runBeforeEach() {
        admin1 = new Admin("hosein", "hoseini", "h.hoseini@gmail.com", "h.hoseini", "hh123456");

    }


    @Test
    void save() {
        adminService.save(admin1);
        Assertions.assertEquals(admin1,adminService.findAdminByUsername(admin1.getUsername()).get());

    }

    @Test
    void changeAdminPassword() {
    }

    @Test
    void createNewService() {
    }

    @Test
    void createNewSubService() {
    }

    @Test
    void showAllServices() {
    }

    @Test
    void findAllSubServiceByService() {
    }

    @Test
    void editSubService() {
    }

    @Test
    void editSubServiceWithDescription() {
    }

    @Test
    void editSubServiceWithBasePrice() {
    }

    @Test
    void addCustomer() {
    }

    @Test
    void addExpert() {
    }

    @Test
    void addExpertWithPicture() {
    }

    @Test
    void addExpertToSubService() {
    }

    @Test
    void deleteExpertFromSubService() {
    }

    @Test
    void expertConfirm() {
    }

    @Test
    void deleteSubService() {
    }
}