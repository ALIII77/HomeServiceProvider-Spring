package com.example.ProjectV2.service.implementation;

import com.example.ProjectV2.entity.*;
import com.example.ProjectV2.entity.builder.*;
import com.example.ProjectV2.entity.enums.ExpertStatus;
import com.example.ProjectV2.repository.SubServiceRepository;
import com.example.ProjectV2.service.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;


@SpringBootTest
class AdminServiceImplTest {

    @Autowired
    private AdminService adminService;

    @Autowired
    private ServiceService serviceService;

    @Autowired
    private SubServiceService subServiceService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private ExpertService expertService;

    @Autowired
    private SubServiceRepository subServiceRepository;


    //ADMIN
    Admin admin1;
    Admin admin2;


    //EXPERT
    Expert expert1;
    Expert expert2;
    Expert expert3;
    Expert expert4;
    Expert expert5;


    //SERVICE
    Service service1;
    Service service2;

    List<Service> serviceList = new ArrayList<>();


    //SUB SERVICE
    SubService subService11;
    SubService subService12;
    SubService subService21;
    SubService subService22;
    SubService subService23;
    SubService subService24;


    //CUSTOMER
    Customer customer1;
    Customer customer2;


    @BeforeEach
    public void runBeforeEach() {

    }


    @Test
    void save() {

        adminService.save(admin1);
        Assertions.assertEquals(admin1, adminService.findAdminByUsername(admin1.getUsername()).get());

    }

    @Test
    void changeAdminPassword() {


        adminService.changeAdminPassword(admin1, "hh123457");
        Assertions.assertEquals("hh123457",
                Objects.requireNonNull(adminService.findAdminByUsername(admin1.getUsername()).orElse(null)).getPassword());

    }

    @Test
    void createNewService() {

        adminService.createNewService(service1);
        Assertions.assertEquals(service1, serviceService.findServiceByName(service1.getName()).get());

        adminService.createNewService(service2);
        Assertions.assertEquals(service2, serviceService.findServiceByName(service2.getName()).get());

    }

    @Test
    void createNewSubService() {

        adminService.createNewSubService(service1.getName(), subService11);
        Assertions.assertTrue(serviceService.findServiceByName(service1.getName()).get()
                .getSubServices().contains(subService11));

        adminService.createNewSubService(service1.getName(), subService12);
        Assertions.assertTrue(serviceService.findServiceByName(service1.getName()).get()
                .getSubServices().contains(subService12));

        adminService.createNewSubService(service2.getName(), subService21);
        Assertions.assertTrue(serviceService.findServiceByName(service2.getName()).get()
                .getSubServices().contains(subService21));

        adminService.createNewSubService(service2.getName(), subService22);
        Assertions.assertTrue(serviceService.findServiceByName(service2.getName()).get()
                .getSubServices().contains(subService22));

        adminService.createNewSubService(serviceService.findServiceByName(service2.getName()).get().getName(), subService23);
        Assertions.assertTrue(serviceService.findServiceByName(service2.getName()).get()
                .getSubServices().contains(subService23));
        adminService.createNewSubService(serviceService.findServiceByName(service2.getName()).get().getName(), subService24);
        Assertions.assertTrue(serviceService.findServiceByName(service2.getName()).get()
                .getSubServices().contains(subService24));

    }

    @Test
    void showAllServices() {
        Assertions.assertEquals(serviceList, adminService.showAllServices());
    }

    @Test
    void editSubServiceWithDescription() {

        adminService.editSubServiceWithDescription(subService11, "Design With Ornamental Stone and brick");
        Assertions.assertEquals("Design With Ornamental Stone and brick"
                , subServiceService.findSubServiceByName(subService11.getName()).get().getDescription());

    }

    @Test
    void editSubServiceWithBasePrice() {

        adminService.editSubServiceWithBasePrice(subService11, 27000000);
        Assertions.assertEquals(27000000
                , subServiceService.findSubServiceByName(subService11.getName()).get().getBasePrice());

    }

    @Test
    void addCustomer() {

        adminService.addCustomer(customer1);
        Assertions.assertEquals(customer1, customerService.findCustomerByUsername(customer1.getUsername()).get());

        adminService.addCustomer(customer2);
        Assertions.assertEquals(customer2, customerService.findCustomerByUsername(customer2.getUsername()).get());

    }

    @Test
    void addExpert() {

        adminService.addExpert(expert1);
        Assertions.assertEquals(expert1, expertService.findExpertByUsername(expert1.getUsername()).get());

        adminService.addExpert(expert2);
        Assertions.assertEquals(expert2, expertService.findExpertByUsername(expert2.getUsername()).get());

        adminService.addExpert(expert3);
        Assertions.assertEquals(expert3, expertService.findExpertByUsername(expert3.getUsername()).get());

        adminService.addExpert(expert4);
        Assertions.assertEquals(expert4, expertService.findExpertByUsername(expert4.getUsername()).get());

        adminService.addExpert(expert5);
        Assertions.assertEquals(expert5, expertService.findExpertByUsername(expert5.getUsername()).get());


    }

    @Test
    void addExpertWithPicture() {


        adminService.addExpertWithPicture(expertService.findExpertByUsername(expert3.getUsername()).get()
                , new File("G:\\Picture\\3068450.jpg"));


    }

    @Test
    void findExpertByUsername() throws IOException {


        Expert expert = expertService.findExpertByUsername(expert1.getUsername()).get();
        Assertions.assertTrue(Arrays.equals(expert.getImage()
                , Files.readAllBytes(Paths.get("G:\\Picture\\3068450.jpg"))));


    }


    @Test
    void expertConfirm() {


        adminService.expertConfirm(expertService.findExpertByUsername("m.mirani").get());
        Assertions.assertEquals(ExpertStatus.CONFIRMED
                , expertService.findExpertByUsername("m.mirani").get().getExpertStatus());


    }

    @Test
    void addExpertToSubService() {

        adminService.addExpertToSubService(expertService.findExpertByUsername("o.nobahari")
                .get(), subServiceService.findSubServiceByName(subService11.getName()).get().getName());
        SubService findSubService = subServiceService.findSubServiceByName(subService11.getName()).get();
        Expert findExpert = expertService.findExpertByUsername(expert1.getUsername()).get();
        Assertions.assertTrue(findExpert.getSubServiceSet().contains(findSubService));
        /*Assertions.assertTrue(findSubService.getExpertSet().contains(findExpert));*/


    }

    @Test
    void deleteExpertFromSubService() {


        expertService.deleteExpertFromSubService(expertService.findExpertByUsername("x.xxx").get()
                , subService11.getName());
        Assertions.assertFalse(subServiceService.findSubServiceByName(subService11.getName()).get()
                .getExpertSet().contains(expertService.findExpertByUsername(expert5.getUsername()).get()));

    }


    @Test
    void deleteSubService() {

        subServiceService.deleteSubService(subServiceService.findSubServiceByName("xxz yyz").get());
        Assertions.assertNull(subServiceService.findSubServiceByName("xxz yyz").orElse(null));

    }


}