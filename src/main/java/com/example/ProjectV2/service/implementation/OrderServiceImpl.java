package com.example.ProjectV2.service.implementation;

import com.example.ProjectV2.entity.Customer;
import com.example.ProjectV2.entity.Order;
import com.example.ProjectV2.entity.SubService;
import com.example.ProjectV2.exception.CustomizedIllegalArgumentException;
import com.example.ProjectV2.exception.NotFoundException;
import com.example.ProjectV2.exception.PermissionDeniedException;
import com.example.ProjectV2.repository.CustomerRepository;
import com.example.ProjectV2.repository.OrderRepository;
import com.example.ProjectV2.repository.SubServiceRepository;
import com.example.ProjectV2.service.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;


@org.springframework.stereotype.Service
public class OrderServiceImpl implements OrderService {


    @Autowired
    public OrderServiceImpl() {

    }

}
