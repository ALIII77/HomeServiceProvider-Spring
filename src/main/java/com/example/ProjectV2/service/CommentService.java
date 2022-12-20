package com.example.ProjectV2.service;

import com.example.ProjectV2.entity.Comment;
import java.util.Optional;

public interface CommentService  {

    void saveComment(Comment comment);
    /*
        void addComment(String customerName,Long orderId,double score,String comment,String expertUsername);  //check shavad
    */
    void addComment(Comment newComment,Long orderId);

    Optional<Comment>findCommentById(Long id);


}
