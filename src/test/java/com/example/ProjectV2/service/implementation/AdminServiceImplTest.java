package com.example.ProjectV2.service.implementation;

import com.example.ProjectV2.entity.*;
import com.example.ProjectV2.entity.builder.*;
import com.example.ProjectV2.enums.ExpertStatus;
import com.example.ProjectV2.repository.SubServiceRepository;
import com.example.ProjectV2.service.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

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


        //ADMIN
//        admin1 = new AdminBuilder().firstName("hosein").lastName("hoseini").email("h.hoseini@gmail.com")
//                .username("h.hoseini").password("hh123456").build();
//        admin2 = new AdminBuilder().firstName("amir").lastName("amiri").email("a.amiri@gmail.com")
//                .username("a.amiri").password("aa123456").build();


        //EXPERT
//        expert1 = new ExpertBuilder().firstName("Omid").lastName("Nobahari").email("o.nobahari@gmail.com")
//                .username("o.nobahari").password("on123456").image(null).build();
//        expert2 = new ExpertBuilder().firstName("Morteza").lastName("Mirani").email("m.mirani@gmail.com")
//                .username("m.mirani").password("mm123456").image(null).build();
        expert3 = new ExpertBuilder().firstName("Hasan").lastName("Tabrizi").email("h.tabrizi@gmail.com")
                .username("h.tabrizi").password("ht123456").image(null).build();
//        expert4 = new ExpertBuilder().firstName("Mohamad").lastName("Kermani").email("m.kermani@gmail.com")
//                .username("m.kermani").password("mk123456").image(null).build();
//        expert5 = new ExpertBuilder().firstName("xxxx").lastName("xxxx").email("x.xxxxx@gmail.com")         //for delete
//                .username("x.xxx").password("xx123456").image(null).build();


        //SERVICE
//        service1 = new ServiceBuilder().name("Building Decoration").build();
//        service2 = new ServiceBuilder().name("Hygiene").build();

//        serviceList.add(serviceService.findServiceByName("Building Decoration").get());
//        serviceList.add(serviceService.findServiceByName("Hygiene").get());


        //SUB SERVICE
//        subService11 = new SubServiceBuilder().name("FirePlace").description("Design With Ornamental Stone")
//                .basePrice(2500000).build();
//        subService12 = new SubServiceBuilder().name("Cabinet").description("Kitchen MDF Cabinet Designing Up To 10 Meter")
//                .basePrice(21000000).build();
//        subService21 = new SubServiceBuilder().name("Car wash").description("Automobile")
//                .basePrice(500000).build();
//        subService22 = new SubServiceBuilder().name("House Cleaning").description("Up To 150 Meters")
//                .basePrice(700000).build();
//        subService23 = new SubServiceBuilder().name("xx yy").description("xy")                              //for delete
//                .basePrice(111111).build();
//        subService24 = new SubServiceBuilder().name("xxz yyz").description("xyz")                              //for delete
//                .basePrice(1333311).build();


        //CUSTOMER
//        customer1 = new CustomerBuilder().firstName("Mona").lastName("Yousefi").email("m.yousefi@gmail.com")
//                .username("m.yousefi").password("my123457").build();
//
//        customer2 = new CustomerBuilder().firstName("Dariush").lastName("Farokhi").email("d.farokhi@gmail.com")
//                .username("d.farokhi").password("df123456").build();


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
//        Assertions.assertEquals(service1, serviceService.findServiceByName(service1.getName()).get());
//
//        adminService.createNewService(service2);
//        Assertions.assertEquals(service2, serviceService.findServiceByName(service2.getName()).get());

    }

    @Test
    void createNewSubService() {

//        adminService.createNewSubService(service1.getName(),subService11);
//        Assertions.assertTrue(serviceService.findServiceByName(service1.getName()).get()
//                .getSubServices().contains(subService11));
//
//        adminService.createNewSubService(service1.getName(),subService12);
//        Assertions.assertTrue(serviceService.findServiceByName(service1.getName()).get()
//                .getSubServices().contains(subService12));
//
//        adminService.createNewSubService(service2.getName(),subService21);
//        Assertions.assertTrue(serviceService.findServiceByName(service2.getName()).get()
//                .getSubServices().contains(subService21));
//
//        adminService.createNewSubService(service2.getName(),subService22);
//        Assertions.assertTrue(serviceService.findServiceByName(service2.getName()).get()
//                .getSubServices().contains(subService22));
//
//        adminService.createNewSubService(serviceService.findServiceByName(service2.getName()).get().getName(), subService23);
//        Assertions.assertTrue(serviceService.findServiceByName(service2.getName()).get()
//                .getSubServices().contains(subService23));
//        adminService.createNewSubService(serviceService.findServiceByName(service2.getName()).get().getName(), subService24);
//        Assertions.assertTrue(serviceService.findServiceByName(service2.getName()).get()
//                .getSubServices().contains(subService24));

    }

    @Test
    void showAllServices() {
//        Assertions.assertEquals(serviceList, adminService.showAllServices());
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
//        Assertions.assertEquals(customer1, customerService.findCustomerByUsername(customer1.getUsername()).get());
//
//        adminService.addCustomer(customer2);
//        Assertions.assertEquals(customer2, customerService.findCustomerByUsername(customer2.getUsername()).get());

    }

    @Test
    void addExpert() {

//        adminService.addExpert(expert1);
//        Assertions.assertEquals(expert1, expertService.findExpertByUsername(expert1.getUsername()).get());
//
//                adminService.addExpert(expert2);
//        Assertions.assertEquals(expert2, expertService.findExpertByUsername(expert2.getUsername()).get());
//
//                adminService.addExpert(expert3);
//        Assertions.assertEquals(expert3, expertService.findExpertByUsername(expert3.getUsername()).get());
//
//        adminService.addExpert(expert4);
//        Assertions.assertEquals(expert4, expertService.findExpertByUsername(expert4.getUsername()).get());


//        adminService.addExpert(expert5);
//        Assertions.assertEquals(expert5, expertService.findExpertByUsername(expert5.getUsername()).get());


    }

    @Test
    void addExpertWithPicture() {


        adminService.addExpertWithPicture(expertService.findExpertByUsername(expert3.getUsername()).get()
                ,new File("G:\\Picture\\3068450.jpg"));


    }

    @Test
    void findExpertByUsername() throws IOException {


//        Expert expert = expertService.findExpertByUsername(expert1.getUsername()).get();
//        Assertions.assertTrue(Arrays.equals(expert.getImage()
//                ,Files.readAllBytes(Paths.get("G:\\Picture\\3068450.jpg"))));


    }


    @Test
    void expertConfirm() {


//        adminService.expertConfirm(expertService.findExpertByUsername("m.mirani").get());
//        Assertions.assertEquals(ExpertStatus.CONFIRMED
//                ,expertService.findExpertByUsername("m.mirani").get().getExpertStatus());


    }

    @Test
    void addExpertToSubService() {

//        adminService.addExpertToSubService(expertService.findExpertByUsername("o.nobahari")
//                .get(), subServiceService.findSubServiceByName(subService11.getName()).get().getName());
//        SubService findSubService = subServiceService.findSubServiceByName(subService11.getName()).get();
//        Expert findExpert = expertService.findExpertByUsername(expert1.getUsername()).get();
//        Assertions.assertTrue(findExpert.getSubServiceSet().contains(findSubService));
//        /*Assertions.assertTrue(findSubService.getExpertSet().contains(findExpert));*/


    }

    @Test
    void deleteExpertFromSubService() {


//        expertService.deleteExpertFromSubService(expertService.findExpertByUsername("x.xxx").get()
//                ,subService11.getName());
//        Assertions.assertFalse(subServiceService.findSubServiceByName(subService11.getName()).get()
//                .getExpertSet().contains(expertService.findExpertByUsername(expert5.getUsername()).get()));

    }


    @Test
    void deleteSubService() {

//        subServiceService.deleteSubService(subServiceService.findSubServiceByName("xxz yyz").get());
//        Assertions.assertNull(subServiceService.findSubServiceByName("xxz yyz").orElse(null));

    }


}