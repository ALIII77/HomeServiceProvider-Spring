package com.example.ProjectV2.service;


import com.example.ProjectV2.entity.*;
import com.example.ProjectV2.entity.enums.OrderStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface CustomerService {

    Customer save(Customer customer);

    void changePassword(Customer customer, String newPassword);

    boolean loginCustomer(String username, String password);

    Optional<Customer> findCustomerByUsername(String username);

    Optional<Customer> findById(Long id);

    List<Service> showAllServices();

    List<SubService> showAllSubServiceByService(Service service);

    boolean checkUsername(String username);

    void cartToCartPaymentWages(Long orderId,double amount);

    void onlinePayment(Long orderId,double amount);

    List<Customer> showAllCustomer();

    void deleteCustomer(String username);

    List<Customer> searchCustomer(Map<String, String> predicateMap);

    void credit(Long orderId , double amount);

    void online (Long orderId , double amount);

    boolean verify(String code);

    List<Customer>findAllCustomerByDateOfRegistration(LocalDateTime localDateTime);

    List<Customer> customerReport (Map<String, String> predicateMap);

    List<Order>customerOrderProfile(Map<String,String>predicateMap);

    Credit findCreditByCustomerId (Long customerId);









}
