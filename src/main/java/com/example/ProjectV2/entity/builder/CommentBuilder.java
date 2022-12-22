package com.example.ProjectV2.entity.builder;


import com.example.ProjectV2.entity.Comment;
import com.example.ProjectV2.entity.Customer;
import com.example.ProjectV2.entity.Expert;
import com.example.ProjectV2.entity.Order;


public class CommentBuilder {
    private Customer customer;
    private Expert expert;
    private String text;
    private double score;
    private Order order;

    public CommentBuilder() {
    }

    public static CommentBuilder builder() {
        return new CommentBuilder();
    }

    public CommentBuilder customer(Customer customer) {
        this.customer = customer;
        return this;
    }

    public CommentBuilder expert(Expert expert) {
        this.expert = expert;
        return this;
    }

    public CommentBuilder text(String text) {
        this.text = text;
        return this;
    }

    public CommentBuilder score(double score) {
        this.score = score;
        return this;
    }

    public CommentBuilder order(Order order) {
        this.order = order;
        return this;
    }

    public Comment build() {
        return new Comment(customer, expert, text, score, order);
    }

    public String toString() {
        return "Comment.CommentBuilder(customer=" + this.customer + ", expert=" + this.expert + ", text=" + this.text + ", score=" + this.score + ", order=" + this.order + ")";
    }
}