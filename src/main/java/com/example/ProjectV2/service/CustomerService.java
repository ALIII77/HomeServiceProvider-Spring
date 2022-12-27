package com.example.ProjectV2.service;


import com.example.ProjectV2.entity.*;

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

}
