package com.example.ProjectV2.service.implementation;

import com.example.ProjectV2.entity.*;
import com.example.ProjectV2.exception.CustomizedIllegalArgumentException;
import com.example.ProjectV2.exception.NotFoundException;
import com.example.ProjectV2.exception.PermissionDeniedException;
import com.example.ProjectV2.repository.CustomerRepository;
import com.example.ProjectV2.service.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;


@org.springframework.stereotype.Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final OrderService orderService;
    private final CommentService commentService;
    private final ServiceService serviceService;
    private final SubServiceService subServiceService;
    private final CustomerService customerService;

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository, OrderService orderService, CommentService commentService, ServiceService serviceService, SubServiceService subServiceService, CustomerService customerService) {
        this.customerRepository = customerRepository;

        this.orderService = orderService;
        this.commentService = commentService;
        this.serviceService = serviceService;
        this.subServiceService = subServiceService;
        this.customerService = customerService;
    }

    @Transactional
    @Override
    public Customer save(@Valid Customer customer) {
        try {
            return customerRepository.save(customer);
        } catch (CustomizedIllegalArgumentException exception) {
            System.out.println(exception.getMessage());
        }
        return null;
    }


    @Transactional
    @Override
    public void changePassword(Customer customer, String newPassword) {     //test ok
        Optional<Customer> customerOptional = customerRepository.findCustomerByUsername(customer.getUsername());
        if (customerOptional.isEmpty()) {
            throw new NotFoundException("Not found customer to change password");
        }
        if (!customer.getPassword().equals(customerOptional.get().getPassword())) {
            throw new PermissionDeniedException("password incorrect");
        }
        System.out.println("Customer Find!");
        Customer findCustomer = customerOptional.get();
        if ((findCustomer.getPassword().equals(newPassword))) {
            throw new PermissionDeniedException("Enter new password that not equal with old password");
        }
        findCustomer.setPassword(newPassword);
        try {
            customerRepository.save(findCustomer);
        } catch (CustomizedIllegalArgumentException exception) {
            System.out.println(exception.getMessage());
        }
    }


    @Transactional
    @Override
    public boolean loginCustomer(String username, String password) {
        Optional<Customer> optionalCustomer = customerRepository.findCustomerByUsername(username);
        if (optionalCustomer.isPresent()) {
            if (Objects.equals(optionalCustomer.get().getPassword(), password)) {
                System.out.println("WellCome");
                return true;
            }
            throw new PermissionDeniedException(" incorrect password ");
        }
        throw new NotFoundException(" Not fount Customer");
    }


    @Override
    public Optional<Customer> findCustomerByUsername(String username) {
        return customerRepository.findCustomerByUsername(username);
    }

    @Override
    public Optional<Customer> findById(Long id) {
        return customerRepository.findById(id);
    }


    @Transactional
    @Override
    public void addOrder(String customerUsername, String description, double purposedPrice, String address, String subServiceName) {
        orderService.addOrder(customerUsername, description, purposedPrice, address, subServiceName);
    }

    @Override
    public void addComment(Comment comment, Long orderId) {
        commentService.addComment(comment, orderId);
    }

    @Override
    public List<Service> showAllServices() {
        return serviceService.findAll();
    }

    @Override
    public List<SubService> showAllSubServiceByService(Service service) {
        return subServiceService.findAllSubServicesByService(service);
    }


    @Transactional
    @Override
    public void selectExpert(Long offerId, Long customerId) {
        customerService.selectExpert(offerId, customerId);
    }

    @Transactional
    @Override
    public void changeOrderStatusToStarted(Order order) {
        orderService.changeOrderStatusToStarted(order);
    }

    @Transactional
    @Override
    public void changeOrderStatusToDone(Order order, Offer offer) {
        orderService.changeOrderStatusToDone(order, offer);
    }


}

