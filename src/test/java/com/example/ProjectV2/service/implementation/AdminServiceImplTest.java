package com.example.ProjectV2.service.implementation;

import com.example.ProjectV2.entity.Admin;
import com.example.ProjectV2.service.AdminService;
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


    }


    @Test
    void save() {
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