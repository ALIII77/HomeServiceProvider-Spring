package com.example.ProjectV2.service.implementation;

import com.example.ProjectV2.entity.Expert;
import com.example.ProjectV2.entity.Offer;
import com.example.ProjectV2.entity.builder.OfferBuilder;
import com.example.ProjectV2.service.ExpertService;
import com.example.ProjectV2.service.OfferService;
import com.example.ProjectV2.service.OrderService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ExpertServiceImplTest {


    @Autowired
    private ExpertService expertService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private OfferService offerService;


    //EXPERT
    Expert expert1;


    //OFFER
    Offer offer1;


    @BeforeEach
    public void runBeforeEach() {

        //EXPERT
        expert1 = expertService.findExpertByUsername("o.nobahari").get();

        //OFFER
        offer1 = new OfferBuilder()
                .offerDate(LocalDateTime.of(2022, 12, 26, 17, 30, 40, 40))
                .order(orderService.findOrderById(2L).get())
                .price(25200000)
                .build();

    }


    @Test
    void save() {
    }

    @Test
    void saveExpertWithPicture() {
    }

    @Test
    void changePassword() {
    }

    @Test
    void testChangePassword() {
    }

    @Test
    void loginExpert() {
    }

    @Test
    void addExpertToSubService() {
    }

    @Test
    void expertConfirm() {
    }

    @Test
    void selectExpert() {
    }


    @Test
    void addOffer() {

//     offerService.addOffer(offer1,expert1.getId());
//        Assertions.assertEquals(offer1,offerService.findOfferById(1L).get());

    }
}