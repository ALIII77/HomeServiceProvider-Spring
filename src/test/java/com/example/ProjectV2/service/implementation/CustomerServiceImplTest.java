package com.example.ProjectV2.service.implementation;

import com.example.ProjectV2.entity.*;
import com.example.ProjectV2.entity.builder.*;
import com.example.ProjectV2.service.CustomerService;
import com.example.ProjectV2.service.ExpertService;
import com.example.ProjectV2.service.OrderService;
import com.example.ProjectV2.service.SubServiceService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class CustomerServiceImplTest {


    @Autowired
    private CustomerService customerService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private SubServiceService subServiceService;

    @Autowired
    private ExpertService expertService;


    //CUSTOMER
    Customer customer1;


    //ORDER
    Order order1;


    //SUB SERVICE
    SubService subService11;


    //COMMENT
    Comment comment1;


    //OFFER
    Offer offer1;


    //EXPERT
    Expert expert1;


    @BeforeEach
    public void runBeforeEach() {

        //CUSTOMER
        customer1 = new CustomerBuilder().firstName("Mona").lastName("Yousefi").email("m.yousefi@gmail.com")
                .username("m.yousefi").password("my123457").build();




        //SUB SERVICE
//        subService11 = new SubServiceBuilder().name("FirePlace").description("Design With Ornamental Stone")
//                .basePrice(2500000).build();




        //ORDER
        order1 = new OrderBuilder().subService(subService11).address("tehran-vanak").proposedPrice(2500000)
                .jobDescription("I have a fire place in my home")
                .executionDate(LocalDateTime.of(2022, 12, 25, 17, 30, 30, 30))
                .build();




        //EXPERT
//        expert1 = expertService.findExpertByUsername("o.nobahari").get();





        //OFFER
//        offer1 = new OfferBuilder().expert(expert1)
//                .offerDate(LocalDateTime.of(2022,12,26,17,30,40,40))
//                .order(orderService.findOrderById(5L).get())
//                .price(25200000)
//                .build();




        //COMMENT
//        comment1 = new CommentBuilder().customer(customerService.findCustomerByUsername("m.yousefi").get())
//                .expert()
//                .build();


    }


    @Test
    void changePassword() {

//        customerService.changePassword(customerService.findCustomerByUsername("m.yousefi").get(), "my123459");
//        Assertions.assertEquals("my123459",
//                Objects.requireNonNull(customerService.findCustomerByUsername(customer1.getUsername()).orElse(null)).getPassword());

    }

    @Test
    void loginCustomer() {
    }

    @Test
    void addOrder() {

//        orderService.addOrder(order1,customerService.findCustomerByUsername(customer1.getUsername()).get().getId()
//                ,subServiceService.findSubServiceById(1L).get().getName());
//        Assertions.assertEquals(order1,orderService.findOrderById(2L).get());

    }

    @Test
    void addComment() {

    }

    @Test
    void showAllServices() {
    }

    @Test
    void showAllSubServiceByService() {
    }

    @Test
    void selectExpert() {
    }

    @Test
    void changeOrderStatusToStarted() {
    }

    @Test
    void changeOrderStatusToDone() {
    }
}