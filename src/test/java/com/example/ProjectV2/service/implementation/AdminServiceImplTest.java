package com.example.ProjectV2.service.implementation;

import com.example.ProjectV2.entity.Admin;
import com.example.ProjectV2.entity.Customer;
import com.example.ProjectV2.entity.Service;
import com.example.ProjectV2.entity.SubService;
import com.example.ProjectV2.entity.builder.*;
import com.example.ProjectV2.service.AdminService;
import com.example.ProjectV2.service.CustomerService;
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

    @Autowired
    private CustomerService customerService;






    //ADMIN
    Admin admin1;
    Admin admin2;






    //SERVICE
    Service service1;
    Service service2;



    //SUB SERVICE
    SubService subService11;
    SubService subService12;



    //CUSTOMER
    Customer customer1;
    Customer customer2;







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





        //SUB SERVICE
        subService11 = new SubServiceBuilder().name("FirePlace").description("Design With Ornamental Stone")
                .basePrice(2500000).build();
        subService12 = new SubServiceBuilder().name("Cabinet").description("Kitchen MDF Cabinet Designing Up To 10 Meter")
                .basePrice(21000000).build();




        //CUSTOMER
        customer1 = new CustomerBuilder().firstName("Mona").lastName("Yousefi").email("m.yousefi@gmail.com")
                .username("m.yousefi").password("my123457").build();

        customer2 = new CustomerBuilder().firstName("Dariush").lastName("Farokhi").email("d.farokhi@gmail.com")
                .username("d.farokhi").password("df123456").build();





    }








    @Test
    void save() {

//        adminService.save(admin1);
//        Assertions.assertEquals(admin1, adminService.findAdminByUsername(admin1.getUsername()).get());

    }

    @Test
    void changeAdminPassword() {

//        adminService.changeAdminPassword(admin1,  "hh123457");
//        Assertions.assertEquals("hh123457",
//                Objects.requireNonNull(adminService.findAdminByUsername(admin1.getUsername()).orElse(null)).getPassword());
    }

    @Test
    void createNewService() {

//        adminService.createNewService(service1);
//        Assertions.assertEquals(service1,serviceService.findServiceByName(service1.getName()).get());

//        adminService.createNewService(service2);
//        Assertions.assertEquals(service2,serviceService.findServiceByName(service2.getName()).get());

    }

    @Test
    void createNewSubService() {
//        adminService.createNewSubService(service1.getName(),subService11);
//        Assertions.assertTrue(serviceService.findServiceByName(service1.getName()).get()
//                .getSubServices().contains(subService11));

//        adminService.createNewSubService(service1.getName(),subService12);
//        Assertions.assertTrue(serviceService.findServiceByName(service1.getName()).get()
//                .getSubServices().contains(subService12));

    }

    @Test
    void showAllServices() {
    }


/*
    @Test
    void findAllSubServiceByService() {
    }
*/


/*    @Test
    void editSubService() {
        adminService.editSubService(subService11.getName(),"Design With Ornamental Stone",27000000);
    }
*/

    @Test
    void editSubServiceWithDescription() {

//        adminService.editSubServiceWithDescription(subService11,"Design With Ornamental Stone and brick");
//        Assertions.assertEquals("Design With Ornamental Stone and brick"
//                ,subServiceService.findSubServiceByName(subService11.getName()).get().getDescription());

    }

    @Test
    void editSubServiceWithBasePrice() {

//        adminService.editSubServiceWithBasePrice(subService11,27000000);
//        Assertions.assertEquals(27000000
//                ,subServiceService.findSubServiceByName(subService11.getName()).get().getBasePrice());

    }

    @Test
    void addCustomer() {

//        adminService.addCustomer(customer1);
//        Assertions.assertEquals(customer1,customerService.findCustomerByUsername(customer1.getUsername()).get());

//        adminService.addCustomer(customer2);
//        Assertions.assertEquals(customer2,customerService.findCustomerByUsername(customer2.getUsername()).get());

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