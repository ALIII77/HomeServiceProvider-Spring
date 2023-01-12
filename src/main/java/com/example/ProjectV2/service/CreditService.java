package com.example.ProjectV2.service;

import com.example.ProjectV2.entity.Credit;
import com.example.ProjectV2.entity.Order;

public interface CreditService {

    void save(Credit credit);

    void withdraw(Credit credit,double amount);

    void deposit(Credit credit,double amount);

    void credit(Long orderId , double amount);

    void online (Long orderId , double amount);

    Credit findCreditByCustomerId(Long customerId);

    double findCreditByExpertId (Long expertId);

}
