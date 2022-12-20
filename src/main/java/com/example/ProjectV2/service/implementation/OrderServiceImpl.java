package com.example.ProjectV2.service.implementation;

import com.example.ProjectV2.entity.Customer;
import com.example.ProjectV2.entity.Order;
import com.example.ProjectV2.entity.SubService;
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



    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, CustomerService customerService, SubServiceService subServiceService) {

        this.orderRepository = orderRepository;
        this.customerService = customerService;
        this.subServiceService = subServiceService;
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

}
