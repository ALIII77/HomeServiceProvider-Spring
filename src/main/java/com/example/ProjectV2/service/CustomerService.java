package com.example.ProjectV2.service;


import com.example.ProjectV2.entity.*;
import java.util.List;
import java.util.Optional;

public interface CustomerService {

    Customer save(Customer customer);

    void changePassword(Customer customer,String newPassword);

    boolean loginCustomer(String username,String password);

    Optional<Customer>findCustomerByUsername(String username);

    Optional<Customer> findById(Long id);

}
