package com.example.ProjectV2.service.implementation;

import com.example.ProjectV2.entity.*;
import com.example.ProjectV2.exception.CustomizedIllegalArgumentException;
import com.example.ProjectV2.exception.NotFoundException;
import com.example.ProjectV2.exception.NotUniqueException;
import com.example.ProjectV2.exception.PermissionDeniedException;
import com.example.ProjectV2.repository.CustomerRepository;
import com.example.ProjectV2.service.*;

import java.util.*;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;


@org.springframework.stereotype.Service
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    ApplicationContext applicationContext;
    private final CustomerRepository customerRepository;
    private final ServiceService serviceService;
    private final SubServiceService subServiceService;
    private final TransactionService transactionService;
    private final CreditService creditService;

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository
            , ServiceService serviceService, SubServiceService subServiceService, TransactionService transactionService, CreditService creditService) {
        this.customerRepository = customerRepository;
        this.serviceService = serviceService;
        this.subServiceService = subServiceService;
        this.transactionService = transactionService;
        this.creditService = creditService;
    }


    @Transactional
    @Override
    public Customer save(@Valid Customer customer) {
        if (checkUsername(customer.getUsername())) {
            return customerRepository.save(customer);
        }
        throw new NotUniqueException("customer username is exists");
    }


    @Transactional
    @Override
    public void changePassword(Customer customer, String newPassword) {
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

    @Override
    public List<Service> showAllServices() {
        return serviceService.findAllServices();
    }

    @Override
    public List<SubService> showAllSubServiceByService(Service service) {
        return subServiceService.findAllSubServicesByService(service);
    }


    @Override
    public boolean checkUsername(String username) {
        return customerRepository.findCustomerByUsername(username).isEmpty();
    }

    @Transactional
    @Override
    public void cartToCartPaymentWages(Long orderId, double amount) {
        creditService.credit(orderId, amount);
    }

    public void onlinePayment(Long orderId, double amount) {
        creditService.online(orderId, amount);
    }

}