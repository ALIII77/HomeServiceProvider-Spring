package com.example.ProjectV2.entity.builder;


import com.example.ProjectV2.entity.*;
import com.example.ProjectV2.enums.ExpertStatus;

import java.time.LocalDateTime;
import java.util.Set;

public  class ExpertBuilder {
    private String firstName;
    private String lastName;
    private String email;
    private String username;
    private String password;
    private LocalDateTime dateOfRegistration;
    private Set<SubService> subServiceSet;
    private Set<Offer> offerSet;
    private Set<Comment> commentSet;
    private ExpertStatus expertStatus;
    private Set<Order> orderSet;
    private Credit credit;
    private double score;
    private byte[] image;

    public ExpertBuilder() {
    }

    public static ExpertBuilder builder() {
        return new ExpertBuilder();
    }

    public ExpertBuilder firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public ExpertBuilder lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public ExpertBuilder email(String email) {
        this.email = email;
        return this;
    }

    public ExpertBuilder username(String username) {
        this.username = username;
        return this;
    }

    public ExpertBuilder password(String password) {
        this.password = password;
        return this;
    }

    public ExpertBuilder dateOfRegistration(LocalDateTime dateOfRegistration) {
        this.dateOfRegistration = dateOfRegistration;
        return this;
    }

    public ExpertBuilder subServiceSet(Set<SubService> subServiceSet) {
        this.subServiceSet = subServiceSet;
        return this;
    }

    public ExpertBuilder offerSet(Set<Offer> offerSet) {
        this.offerSet = offerSet;
        return this;
    }

    public ExpertBuilder commentSet(Set<Comment> commentSet) {
        this.commentSet = commentSet;
        return this;
    }

    public ExpertBuilder expertStatus(ExpertStatus expertStatus) {
        this.expertStatus = expertStatus;
        return this;
    }

    public ExpertBuilder orderSet(Set<Order> orderSet) {
        this.orderSet = orderSet;
        return this;
    }

    public ExpertBuilder credit(Credit credit) {
        this.credit = credit;
        return this;
    }

    public ExpertBuilder score(double score) {
        this.score = score;
        return this;
    }

    public ExpertBuilder image(byte[] image) {
        this.image = image;
        return this;
    }

    public Expert build() {
        return new Expert(firstName, lastName, email, username, password, dateOfRegistration, subServiceSet, offerSet, commentSet, expertStatus, orderSet, credit, score, image);
    }

    public String toString() {
        return "Expert.ExpertBuilder(firstName=" + this.firstName + ", lastName=" + this.lastName + ", email=" + this.email + ", username=" + this.username + ", password=" + this.password + ", dateOfRegistration=" + this.dateOfRegistration + ", subServiceSet=" + this.subServiceSet + ", offerSet=" + this.offerSet + ", commentSet=" + this.commentSet + ", expertStatus=" + this.expertStatus + ", orderSet=" + this.orderSet + ", credit=" + this.credit + ", score=" + this.score + ", image=" + java.util.Arrays.toString(this.image) + ")";
    }
}