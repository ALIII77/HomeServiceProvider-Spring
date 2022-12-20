package com.example.ProjectV2.service.implementation;

import com.example.ProjectV2.entity.Comment;
import com.example.ProjectV2.entity.Expert;
import com.example.ProjectV2.entity.Order;
import com.example.ProjectV2.enums.OrderStatus;
import com.example.ProjectV2.exception.CustomizedIllegalArgumentException;
import com.example.ProjectV2.exception.NotFoundException;
import com.example.ProjectV2.repository.CommentRepository;
import com.example.ProjectV2.repository.ExpertRepository;
import com.example.ProjectV2.repository.OrderRepository;
import com.example.ProjectV2.service.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

@org.springframework.stereotype.Service
public class CommentServiceImpl implements CommentService {


    @Autowired
    public CommentServiceImpl() {
    }



}
