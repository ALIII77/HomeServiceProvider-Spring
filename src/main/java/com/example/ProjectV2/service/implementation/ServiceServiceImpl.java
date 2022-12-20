package com.example.ProjectV2.service.implementation;

import com.example.ProjectV2.entity.Service;
import com.example.ProjectV2.entity.SubService;
import com.example.ProjectV2.exception.CustomizedIllegalArgumentException;
import com.example.ProjectV2.repository.ServiceRepository;
import com.example.ProjectV2.service.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;


@org.springframework.stereotype.Service
public class ServiceServiceImpl implements ServiceService {



    @Autowired
    public ServiceServiceImpl() {
    }



}
