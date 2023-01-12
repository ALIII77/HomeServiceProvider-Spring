package com.example.ProjectV2.service.implementation;

import com.example.ProjectV2.entity.Comment;
import com.example.ProjectV2.entity.Expert;
import com.example.ProjectV2.entity.Order;
import com.example.ProjectV2.entity.enums.OrderStatus;
import com.example.ProjectV2.exception.CustomizedIllegalArgumentException;
import com.example.ProjectV2.exception.NotFoundException;
import com.example.ProjectV2.repository.CommentRepository;
import com.example.ProjectV2.service.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@org.springframework.stereotype.Service
public class CommentServiceImpl implements CommentService {

    private final ApplicationContext applicationContext;
    private final CommentRepository commentRepository;
    private final ExpertService expertService;

    @Autowired
    public CommentServiceImpl(ApplicationContext applicationContext, CommentRepository commentRepository
            , ExpertService expertService) {
        this.applicationContext = applicationContext;
        this.commentRepository = commentRepository;
        this.expertService = expertService;
    }


    @Transactional
    @Override
    public void saveComment(@Valid Comment comment) {
        commentRepository.save(comment);
    }



    @Transactional
    @Override
    public void addComment(Comment newComment, String expertUsername,Long orderId) {
        OrderService orderService = applicationContext.getBean(OrderService.class);
        Expert findExpert = expertService.findExpertByUsername(expertUsername)
                .orElseThrow(() -> new NotFoundException("Not exists expert to add comment"));
        Order findOrder = orderService.findOrderById(orderId).orElseThrow(
                () -> new NotFoundException("Not exists order to add comment"));

        if (findOrder.getOrderStatus() != OrderStatus.PAID) {
            throw new CustomizedIllegalArgumentException(" Order must be Paid state ! ");
        }
        findOrder.setComment(newComment);
        newComment.setExpert(findExpert);
        newComment.setCustomer(findOrder.getCustomer());
        newComment.setOrder(findOrder);
        commentRepository.save(newComment);
        expertService.setSumScore(findExpert.getId(),newComment.getScore());
    }


    @Override
    public Optional<Comment> findCommentById(Long id) {
        return commentRepository.findById(id);
    }

    @Override
    public List<Double> findScoreByExpertId(Long expertId) {
        return commentRepository.findScoreByExpertId(expertId);
    }


}
