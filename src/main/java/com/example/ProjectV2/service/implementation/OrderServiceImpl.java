package com.example.ProjectV2.service.implementation;

import com.example.ProjectV2.entity.*;
import com.example.ProjectV2.enums.ExpertStatus;
import com.example.ProjectV2.enums.OrderStatus;
import com.example.ProjectV2.exception.CustomizedIllegalArgumentException;
import com.example.ProjectV2.exception.NotFoundException;
import com.example.ProjectV2.exception.PermissionDeniedException;
import com.example.ProjectV2.repository.OrderRepository;
import com.example.ProjectV2.service.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;


@org.springframework.stereotype.Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    ApplicationContext applicationContext;
    private final OrderRepository orderRepository;
    private final CustomerService customerService;
    private final SubServiceService subServiceService;
    private final OfferService offerService;


    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, CustomerService customerService
            , SubServiceService subServiceService, OfferService offerService) {
        this.orderRepository = orderRepository;
        this.customerService = customerService;
        this.subServiceService = subServiceService;
        this.offerService = offerService;
    }

    @Transactional
    @Override
    public void save(@Valid Order order) {
        orderRepository.save(order);
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

    }

    @Override
    @Transactional
    public void addOrder(Order order, Long customerId, String subServiceName) {
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
    }


    @Override
    public Optional<Order> findOrderById(Long id) {
        return orderRepository.findById(id);
    }


    @Transactional
    @Override
    public void changeOrderStatusToStarted(Order order) {
        Order findOrder = orderRepository.findById(order.getId())
                .orElseThrow(() -> new NotFoundException("Not found order with id = " + order.getId()));
        if (findOrder.getOrderStatus() != OrderStatus.COMING_EXPERTS) {
            throw new CustomizedIllegalArgumentException
                    ("To set the status of the order to Started, the order must first be in 'COMING EXPERT' status");
        }
        if (LocalDateTime.now().isAfter(findOrder.getAcceptedOffer().getStartDate())
                && LocalDateTime.now().isBefore(findOrder.getAcceptedOffer().getEndDate())) {
            findOrder.setOrderStatus(OrderStatus.STARTED);
            orderRepository.save(findOrder);
        } else {
            throw new CustomizedIllegalArgumentException("The registered time for the order has not yet arrived");
        }
    }


    @Override
    public void changeOrderStatusToDone(Order order) {
        Order findOrder = orderRepository.findById(order.getId())
                .orElseThrow(() -> new NotFoundException("Not found order with id = " + order.getId()));
        if (findOrder.getOrderStatus() != OrderStatus.STARTED) {
            throw new CustomizedIllegalArgumentException
                    ("the execution of the order must first be 'STARTED' state " +
                            ", then it  will be change status order to 'DONE' state");
        }
        findOrder.setOrderStatus(OrderStatus.DONE);
        orderRepository.save(findOrder);
    }

    @Override
    public void changeOrderStatusToPaid(Order order) {
        Order findOrder = orderRepository.findById(order.getId()).get();
        findOrder.setOrderStatus(OrderStatus.PAID);
        orderRepository.save(findOrder);
    }
}
