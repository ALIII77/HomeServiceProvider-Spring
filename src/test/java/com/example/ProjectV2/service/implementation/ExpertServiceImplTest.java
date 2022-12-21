package com.example.ProjectV2.service.implementation;

import com.example.ProjectV2.entity.Expert;
import com.example.ProjectV2.entity.Offer;
import com.example.ProjectV2.entity.builder.ExpertBuilder;
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
    Expert expert4;


    //OFFER
    Offer offer1;


    @BeforeEach
    public void runBeforeEach() {

        //EXPERT
        expert1 = expertService.findExpertByUsername("o.nobahari").get();
        expert4 = new ExpertBuilder().firstName("Mohamad").lastName("Kermani").email("m.kermani@gmail.com")
                .username("m.kermani").password("mk123456").image(null).build();

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

//        expertService.changePassword(expertService.findExpertByUsername(expert4.getUsername()).get(),"mk123459");
//        Assertions.assertEquals("mk123459",expertService.findExpertByUsername(expert4.getUsername()).get().getPassword());

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