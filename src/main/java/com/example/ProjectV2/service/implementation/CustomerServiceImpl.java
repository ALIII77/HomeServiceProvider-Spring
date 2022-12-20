package com.example.ProjectV2.service.implementation;

import com.example.ProjectV2.entity.*;
import com.example.ProjectV2.entity.Service;
import com.example.ProjectV2.enums.OrderStatus;
import com.example.ProjectV2.exception.CustomizedIllegalArgumentException;
import com.example.ProjectV2.exception.NotFoundException;
import com.example.ProjectV2.exception.PermissionDeniedException;
import com.example.ProjectV2.repository.CustomerRepository;
import com.example.ProjectV2.service.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;


@org.springframework.stereotype.Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;

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


}

