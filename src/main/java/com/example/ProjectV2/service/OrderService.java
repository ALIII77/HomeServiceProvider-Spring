package com.example.ProjectV2.service;

import com.example.ProjectV2.entity.Order;
import java.util.Optional;

public interface OrderService {

    void save(Order order);

    void addOrder(String customerUsername,String description,double purposedPrice,String address,String subServiceName);

    void addOrder(Order order,Long customerId, String subServiceName );

    Optional<Order> findOrderById(Long id);


}
