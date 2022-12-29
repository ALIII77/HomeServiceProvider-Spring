package com.example.ProjectV2.service.implementation;

import com.example.ProjectV2.entity.*;
import com.example.ProjectV2.entity.enums.OrderStatus;
import com.example.ProjectV2.service.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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

    }


    @Test
    void changePassword() {

        customerService.changePassword(customerService.findCustomerByUsername("m.yousefi").get(), "my123459");
        Assertions.assertEquals("my123459",
                Objects.requireNonNull(customerService.findCustomerByUsername(customer1.getUsername()).orElse(null)).getPassword());

    }

    @Test
    void loginCustomer() {
    }

    @Test
    void addOrder() {

        orderService.addOrder(order1, customerService.findCustomerByUsername(customer1.getUsername()).get().getId()
                , subServiceService.findSubServiceById(1L).get().getName());
        Assertions.assertEquals(order1, orderService.findOrderById(2L).get());

    }

    @Test
    void addComment() {
//        commentService.addComment(comment1, orderService.findOrderById(2L).get().getId());
//        Assertions.assertEquals(comment1, commentService.findCommentById(13L).get());
    }

    @Test
    void showAllServices() {
    }

    @Test
    void showAllSubServiceByService() {
    }

    @Test
    void selectExpert() {

        expertService.selectExpert(1L, customerService.findCustomerByUsername(customer1.getUsername()).get().getId());
        Assertions.assertEquals(OrderStatus.COMING_EXPERTS, orderService.findOrderById(2L).get().getOrderStatus());

    }

    @Test
    void changeOrderStatusToStarted() {
        orderService.changeOrderStatusToStarted(orderService.findOrderById(2L).get());
        Optional<Order> findOrder = orderService.findOrderById(2L);
        assertSame(findOrder.get().getOrderStatus(), OrderStatus.STARTED);

    }

    @Test
    void changeOrderStatusToDone() {

        orderService.changeOrderStatusToDone(orderService.findOrderById(2L).get());
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
        System.out.println("*******");
        for (Offer o : findOfferList1) {
            System.out.println(o);
        }
    }

    @Test
    void findAllOfferOneOrderByPrice() {

        Order findOrder = orderService.findOrderById(2L).get();
        List<Offer> findOfferList1 = offerService.findAllOfferOneOrderByPrice(2L);
        List<Offer> findOfferList2 = new ArrayList<>(findOrder.getOfferSet());
        findOfferList2 = findOfferList2.stream().sorted(Comparator.comparing((offer -> offer.getPrice()))).toList();
        Assertions.assertEquals(findOfferList2, findOfferList1);
        System.out.println("*****");
        for (Offer o : findOfferList2) {
            System.out.println(o);
        }

    }

}