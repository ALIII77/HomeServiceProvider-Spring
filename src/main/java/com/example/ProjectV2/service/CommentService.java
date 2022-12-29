package com.example.ProjectV2.service;

import com.example.ProjectV2.entity.Comment;
import com.example.ProjectV2.entity.Expert;

import java.util.List;
import java.util.Optional;

public interface CommentService {

    void saveComment(Comment comment);

    void addComment(Comment newComment, String expertUsername, Long orderId);

    Optional<Comment> findCommentById(Long id);

    List<double> findScoreByExpertId(Long expertId);


}
