package com.example.ProjectV2.service.implementation;

import com.example.ProjectV2.entity.Customer;
import com.example.ProjectV2.entity.Offer;
import com.example.ProjectV2.entity.Order;
import com.example.ProjectV2.entity.SubService;
import com.example.ProjectV2.enums.OrderStatus;
import com.example.ProjectV2.exception.CustomizedIllegalArgumentException;
import com.example.ProjectV2.exception.NotFoundException;
import com.example.ProjectV2.exception.PermissionDeniedException;
import com.example.ProjectV2.repository.OrderRepository;
import com.example.ProjectV2.service.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;


@org.springframework.stereotype.Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final CustomerService customerService;
    private final SubServiceService subServiceService;
    private final OrderService orderService;
    private final OfferService offerService;



    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, CustomerService customerService, SubServiceService subServiceService, OrderService orderService, OfferService offerService) {

        this.orderRepository = orderRepository;
        this.customerService = customerService;
        this.subServiceService = subServiceService;
        this.orderService = orderService;
        this.offerService = offerService;
    }




    @Transactional
    @Override
    public void save(@Valid Order order) {
        try {
            orderRepository.save(order);
        } catch (CustomizedIllegalArgumentException exception) {
            System.out.println(exception.getMessage());
        }
    }

    @Transactional
    @Override
    public void addOrder(String customerUsername, String description, double purposedPrice, String address, String subServiceName) {
        Optional<Customer> customerOptional = customerService.findCustomerByUsername(customerUsername);
        Optional<SubService> subServiceOptional = subServiceService.findSubServiceByName(subServiceName);
        if (customerOptional.isEmpty()) {
            throw new NotFoundException("Not found customer to create an order");
        }
        if (subServiceOptional.isEmpty()) {
            throw new NotFoundException("Not found sub service to create an order with customer");
        }
        SubService findSubService = subServiceOptional.get();
        if (purposedPrice < findSubService.getBasePrice()) {
            throw new PermissionDeniedException(
                    "The price of your order must be greater than or equal to the base price of the sub service");
        }
        Customer findCustomer = customerOptional.get();
        Order newOrder = new Order();

        newOrder.setJobDescription(description);
        newOrder.setProposedPrice(purposedPrice);
        newOrder.setAddress(address);
        newOrder.setCustomer(findCustomer);
        newOrder.setSubService(findSubService);
        /*newOrder.setOrderStatus(OrderStatus.WAITING_FOR_SUGGESTION_OF_EXPERTS);*/
        newOrder.setExecutionDate(LocalDateTime.now());
        orderRepository.save(newOrder);

        findSubService.addOrder(newOrder);
        subServiceService.save(findSubService);

        findCustomer.addOrder(newOrder);
        customerService.save(findCustomer);
    }

    @Override
    @Transactional
    public void addOrder(Order order, Long customerId, String subServiceName) {         //test fail
        Customer findCustomer = customerService.findById(customerId)
                .orElseThrow(() -> new NotFoundException("Not found customer to create an order"));
        SubService findSubService = subServiceService.findSubServiceByName(subServiceName)
                .orElseThrow(() -> new NotFoundException("Not found sub service to create an order with customer"));

        if (order.getProposedPrice() < findSubService.getBasePrice()) {
            throw new CustomizedIllegalArgumentException(
                    "The price of your order must be greater than or equal to the base price of the sub service");
        }
        if (order.getExecutionDate().isBefore(LocalDateTime.now())) {
            throw new CustomizedIllegalArgumentException("order execution time must be after now time");
        }
        order.setSubService(findSubService);
        order.setCustomer(findCustomer);
        orderRepository.save(order);


/*            findSubService.addOrder(order);
            subServiceRepository.save(findSubService);

            Customer findCustomer = customerOptional.get();
            findCustomer.addOrder(order);
            customerRepository.save(findCustomer);
            */
    }


    @Override
    public Optional<Order> findOrderById(Long id) {
        return orderRepository.findById(id);
    }


    /*    @Override
    @Transactional
    public void changeOrderStatusToStarted(Long orderId) {
        Order findOrder = orderService.findOrderById(orderId)
                .orElseThrow(() -> new NotFoundException("Not found order with id = " + orderId));
        Offer findOffer = offerService.findOfferByOrderIdAndExpertId(orderId,findOrder.getExpert().getId())
                .orElseThrow(() -> new NotFoundException("Not found Offer with id =     "));
        if (LocalDateTime.now().isAfter(findOffer.getOfferDate())){
            try {
                findOrder.setOrderStatus(OrderStatus.STARTED);
                orderService.save(findOrder);
            } catch (CustomizedIllegalArgumentException exception) {
                System.out.println(exception.getMessage());
            }
        } else {
            throw new PermissionDeniedException("The registered time for the order has not yet arrived");
        }
    }*/




    @Transactional
    @Override
    public void changeOrderStatusToStarted(Order order) {
        if (order.getOrderStatus() != OrderStatus.COMING_EXPERTS) {
            throw new CustomizedIllegalArgumentException
                    ("To set the status of the order to Started, the order must first be in 'COMING EXPERT' status");
        }
        Order findOrder = orderService.findOrderById(order.getId())
                .orElseThrow(() -> new NotFoundException("Not found order with id = " + order.getId()));
        Offer findOffer = offerService.findOfferByOrderIdAndExpertId(order.getId(), findOrder.getExpert().getId())
                .orElseThrow(() -> new NotFoundException("Not exists Offer for order with id = " + order.getId()));
        if (LocalDateTime.now().isAfter(findOffer.getOfferDate())) {
            try {
                findOrder.setOrderStatus(OrderStatus.STARTED);
                orderService.save(findOrder);
            } catch (CustomizedIllegalArgumentException exception) {
                System.out.println(exception.getMessage());
            }
        } else {
            throw new CustomizedIllegalArgumentException("The registered time for the order has not yet arrived");
        }
    }


/*    @Override
    @Transactional
    public void changeOrderStatusToDone(Long orderId, Long offerId) {
        Order findOrder = orderService.findOrderById(orderId)
                .orElseThrow(() -> new NotFoundException("Not found order with id = " + orderId));
        Offer findOffer = offerService.findOfferById(offerId)
                .orElseThrow(() -> new NotFoundException("Not found offer with id = " + offerId));
        if (LocalDateTime.now().isAfter(findOrder.getExecutionDate().plusHours((long) Math.ceil(findOffer.getDuration())))) {
            try {
                findOrder.setOrderStatus(OrderStatus.DONE);
                orderService.save(findOrder);
            } catch (CustomizedIllegalArgumentException exception) {
                System.out.println(exception.getMessage());
            }
        } else {
            throw new PermissionDeniedException("The registered end time of offer has not yet arrived");
        }
    }*/





    @Override
    public void changeOrderStatusToDone(Order order, Offer offer) {
        if (order.getOrderStatus() != OrderStatus.STARTED) {
            throw new CustomizedIllegalArgumentException
                    ("the execution of the order must first be 'STARTED' state , then it  will be change status order to 'DONE' state");
        }
        Order findOrder = orderService.findOrderById(order.getId())
                .orElseThrow(() -> new NotFoundException("Not found order with id = " + order.getId()));
        Offer findOffer = offerService.findOfferById(offer.getId())
                .orElseThrow(() -> new NotFoundException("Not found offer with id = " + offer.getId()));
        if (LocalDateTime.now().isAfter(findOrder.getExecutionDate().plusHours((long) Math.ceil(findOffer.getDuration())))) {
            try {
                findOrder.setOrderStatus(OrderStatus.DONE);
                orderService.save(findOrder);
            } catch (CustomizedIllegalArgumentException exception) {
                System.out.println(exception.getMessage());
            }
        } else {
            throw new PermissionDeniedException("The registered end time of offer has not yet arrived");
        }
    }




}
