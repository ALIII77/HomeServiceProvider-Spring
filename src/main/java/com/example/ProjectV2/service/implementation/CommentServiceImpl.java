package com.example.ProjectV2.service.implementation;

import com.example.ProjectV2.entity.Comment;
import com.example.ProjectV2.entity.Expert;
import com.example.ProjectV2.entity.Order;
import com.example.ProjectV2.enums.OrderStatus;
import com.example.ProjectV2.exception.CustomizedIllegalArgumentException;
import com.example.ProjectV2.exception.NotFoundException;
import com.example.ProjectV2.repository.CommentRepository;
import com.example.ProjectV2.service.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

@org.springframework.stereotype.Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final ExpertService expertService;
    private final OrderService orderService;

    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository, ExpertService expertService, OrderService orderService) {
        this.commentRepository = commentRepository;
        this.expertService = expertService;
        this.orderService = orderService;
    }


    @Transactional
    @Override
    public void saveComment(@Valid Comment comment) {
        try {
            commentRepository.save(comment);
        } catch (CustomizedIllegalArgumentException exception) {
            System.out.println(exception.getMessage());
        }
    }




    /*@Transactional
    @Override
    public void addComment(String customerName, Long orderId, double score, String comment, String expertUsername) {

        Customer findCustomer  = customerRepository.findCustomerByUsername(customerName)
                .orElseThrow(() -> new NotFoundException("Not exists customer to add comment"));

        Expert findExpert  = expertRepository.findExpertByUsername(expertUsername)
                .orElseThrow(() -> new NotFoundException("Not exists expert to add comment"));

        Order findOrder = orderRepository.findById(orderId)
                .orElseThrow(() -> new NotFoundException(" Not order expert to add comment "));

        if (findOrder.getOrderStatus() != OrderStatus.PAID) {
            throw new CustomizedIllegalArgumentException(" Order must be Done state ! ");
        }

        try {
            Comment newComment = new Comment();
            newComment.setCustomer(findCustomer);
            newComment.setExpert(findExpert);
            newComment.setScore(score);
            newComment.setText(comment);
            newComment.setOrder(orderOptional.get());
            commentRepository.save(newComment);
            findCustomer.addComment(newComment);
            customerRepository.save(findCustomer);
            findExpert.addComment(newComment);
            Double averageScore = findExpert.getCommentSet().stream()
                    .mapToDouble(commentScore -> commentScore.getScore()).average().orElse(0);
            findExpert.setScore(averageScore);
            expertRepository.save(findExpert);
        } catch (CustomizedIllegalArgumentException exception) {
            System.out.println(exception.getMessage());
        }
    }*/




    @Transactional
    @Override
    public void addComment(Comment newComment, Long orderId) {
        Expert findExpert = expertService.findExpertByUsername(newComment.getExpert().getUsername())
                .orElseThrow(() -> new NotFoundException("Not exists expert to add comment"));
        Order findOrder = orderService.findOrderById(orderId).orElseThrow(
                () -> new NotFoundException("Not exists order to add comment"));

        if (findOrder.getOrderStatus() != OrderStatus.PAID) {
            throw new CustomizedIllegalArgumentException(" Order must be Paid state ! ");
        }

        newComment.setOrder(findOrder);
        commentRepository.save(newComment);

        findExpert.addComment(newComment);
        Double averageScore = findExpert.getCommentSet().stream()
                .mapToDouble(commentScore -> commentScore.getScore()).average().orElse(0);
        findExpert.setScore(averageScore);
        expertService.save(findExpert);
    }


    @Override
    public Optional<Comment> findCommentById(Long id) {
        return commentRepository.findById(id);
    }


}
