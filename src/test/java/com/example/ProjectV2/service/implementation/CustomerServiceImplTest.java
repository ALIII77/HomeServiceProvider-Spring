package com.example.ProjectV2.service.implementation;

import com.example.ProjectV2.entity.*;
import com.example.ProjectV2.entity.builder.*;
import com.example.ProjectV2.enums.OrderStatus;
import com.example.ProjectV2.service.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.*;

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
    private OfferService offerService;

    @Autowired
    private ExpertService expertService;

    @Autowired
    private CommentService commentService;


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
        expert1 = expertService.findExpertByUsername("o.nobahari").get();


        //OFFER
//        offer1 = new OfferBuilder().expert(expert1)
//                .offerDate(LocalDateTime.of(2022,12,26,17,30,40,40))
//                .order(orderService.findOrderById(5L).get())
//                .price(25200000)
//                .build();


//        COMMENT
        comment1 = new CommentBuilder()
                .customer(customerService.findCustomerByUsername("m.yousefi").get())
                .expert(expertService.findExpertByUsername("o.nobahari").get())
                .order(orderService.findOrderById(2L).get())
                .score(4)
                .text("Perfect!!")
                .build();

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
//                customer1 = customerService.findCustomerByUsername(customer1.getUsername()).orElse(null);
//        commentService.addComment(.getUsername(), 3L, 1, "I regretted", "m.nosrati");
//        comment = commentService.findCommentById(7L).orElse(null);
//        Assertions.assertEquals(customer1.getUsername(), comment.getCustomer().getUsername());

        commentService.addComment(comment1,orderService.findOrderById(2L).get().getId());
        Assertions.assertEquals(comment1,commentService.findCommentById(13L).get());




//        commentService.addComment(comment1,order1.getId());
//        Assertions.assertEquals(comment1,commentService.findCommentById(1L).get());
    }

    @Test
    void showAllServices() {
    }

    @Test
    void showAllSubServiceByService() {
    }

    @Test
    void selectExpert() {

//        expertService.selectExpert(1L,customerService.findCustomerByUsername(customer1.getUsername()).get().getId());
//        Assertions.assertEquals(OrderStatus.COMING_EXPERTS,orderService.findOrderById(2L).get().getOrderStatus());

    }

    @Test
    void changeOrderStatusToStarted() {
        orderService.changeOrderStatusToStarted(orderService.findOrderById(2L).get());
        Optional<Order> findOrder = orderService.findOrderById(2L);
        assertSame(findOrder.get().getOrderStatus(), OrderStatus.STARTED);

    }

    @Test
    void changeOrderStatusToDone() {

        orderService.changeOrderStatusToDone(orderService.findOrderById(2L).get(), offerService.findOfferById(1L).get());
        Assertions.assertEquals(OrderStatus.DONE, orderService.findOrderById(2L).get().getOrderStatus());

    }

    @Test
    void findAllOfferOneOrderByExpertScore() {

        Order findOrder = orderService.findOrderById(2L).get();
        List<Offer> findOfferList1 = offerService.findAllOfferOneOrderByExpertScore(2L);
        List<Offer> findOfferList2 = new ArrayList<>(findOrder.getOfferSet());
        Comparator<Double> comparator = (d1, d2) -> Double.compare(d2, d1);
        findOfferList2 = findOfferList2.stream()
                .sorted(Comparator.comparing((offer -> offer.getExpert().getScore()), comparator)).toList();
        Assertions.assertEquals(findOfferList2, findOfferList1);
        System.out.println("***********************************************************************");
        System.out.println("***********************************************************************");
        System.out.println("***********************************************************************");
        System.out.println("***********************************************************************");
        for (Offer o : findOfferList1) {
            System.out.println(o);
        }
//        System.out.println(findOfferList2);


    }

    @Test
    void findAllOfferOneOrderByPrice() {

        Order findOrder = orderService.findOrderById(2L).get();
        List<Offer> findOfferList1 = offerService.findAllOfferOneOrderByPrice(2L);
        List<Offer> findOfferList2 = new ArrayList<>(findOrder.getOfferSet());
        findOfferList2 = findOfferList2.stream().sorted(Comparator.comparing((offer -> offer.getPrice()))).toList();
        Assertions.assertEquals(findOfferList2, findOfferList1);

        System.out.println("***********************************************************************");
        System.out.println("***********************************************************************");
        System.out.println("***********************************************************************");
        System.out.println("***********************************************************************");

        for (Offer o : findOfferList2) {
            System.out.println(o);
        }


    }

}