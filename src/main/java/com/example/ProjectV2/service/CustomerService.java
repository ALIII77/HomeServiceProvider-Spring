package com.example.ProjectV2.service;


import com.example.ProjectV2.entity.*;

import java.util.List;
import java.util.Optional;

public interface CustomerService {

    Customer save(Customer customer);

    void changePassword(Customer customer, String newPassword);

    boolean loginCustomer(String username, String password);

    Optional<Customer> findCustomerByUsername(String username);

    Optional<Customer> findById(Long id);

/*
    void addOrder(String customerUsername, String description, double purposedPrice, String address, String subServiceName);
*/

/*
    void addComment(String customerName,Long orderId,double score,String comment,String expertUsername);
*/

    List<Service> showAllServices();

    List<SubService> showAllSubServiceByService(Service service);


/*
    void changeOrderStatusToStarted(Long orderId); //
*/
/*
    void changeOrderStatusToStarted(Order order);//
*/

/*
    void changeOrderStatusToDone(Long orderId ,Long offerId);
*/
/*
    void changeOrderStatusToDone(Order order, Offer offer);
*/

}
