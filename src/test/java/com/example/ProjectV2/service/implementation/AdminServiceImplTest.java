package com.example.ProjectV2.service.implementation;

import com.example.ProjectV2.entity.Admin;
import com.example.ProjectV2.entity.Service;
import com.example.ProjectV2.entity.builder.AdminBuilder;
import com.example.ProjectV2.entity.builder.OrderBuilder;
import com.example.ProjectV2.entity.builder.ServiceBuilder;
import com.example.ProjectV2.entity.builder.SubServiceBuilder;
import com.example.ProjectV2.service.AdminService;
import com.example.ProjectV2.service.ServiceService;
import com.example.ProjectV2.service.SubServiceService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Objects;


@SpringBootTest
class AdminServiceImplTest {


    @Autowired
    private AdminService adminService;

    @Autowired
    private ServiceService serviceService;

    @Autowired
    private SubServiceService subServiceService;






    //ADMIN
    Admin admin1;
    Admin admin2;






    //SERVICE
    Service service1;
    Service service2;



    //SUB SERVICE










    @BeforeEach
    public void runBeforeEach() {


        //ADMIN
        admin1 = new AdminBuilder().firstName("hosein").lastName("hoseini").email("h.hoseini@gmail.com")
                .username("h.hoseini").password("hh123456").build();
        admin2 = new AdminBuilder().firstName("amir").lastName("amiri").email("a.amiri@gmail.com")
                .username("a.amiri").password("aa123456").build();



        //SERVICE
        service1 = new ServiceBuilder().name("Building Decoration").build();
        service2 = new ServiceBuilder().name("Hygiene").build();






    }








    @Test
    void save() {

        adminService.save(admin1);
        Assertions.assertEquals(admin1, adminService.findAdminByUsername(admin1.getUsername()).get());

    }

    @Test
    void changeAdminPassword() {

        adminService.changeAdminPassword(admin1,  "hh123457");
        Assertions.assertEquals("hh123457",
                Objects.requireNonNull(adminService.findAdminByUsername(admin1.getUsername()).orElse(null)).getPassword());
    }

    @Test
    void createNewService() {

        adminService.createNewService(service1);
        Assertions.assertEquals(service1,serviceService.findServiceByName(service1.getName()).get());

/*        adminService.createNewService(service2);
        Assertions.assertEquals(service2,serviceService.findServiceByName(service2.getName()).get());*/

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