package com.example.ProjectV2.service;

import com.example.ProjectV2.entity.Offer;
import com.example.ProjectV2.entity.Order;

import java.util.Optional;

public interface OrderService {

    void save(Order order);

    void addOrder(String customerUsername, String description, double purposedPrice, String address, String subServiceName);

    void addOrder(Order order, Long customerId, String subServiceName);

    Optional<Order> findOrderById(Long id);

    /*
        void changeOrderStatusToStarted(Long orderId); //
    */
    void changeOrderStatusToStarted(Order order);//

    /*
        void changeOrderStatusToDone(Long orderId ,Long offerId);
    */
    void changeOrderStatusToDone(Order order);

    void changeOrderStatusToPaid(Order order);


}
