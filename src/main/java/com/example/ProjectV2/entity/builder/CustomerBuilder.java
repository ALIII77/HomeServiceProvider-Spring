package com.example.ProjectV2.entity.builder;


import com.example.ProjectV2.entity.Comment;
import com.example.ProjectV2.entity.Credit;
import com.example.ProjectV2.entity.Customer;
import com.example.ProjectV2.entity.Order;

import java.time.LocalDateTime;
import java.util.Set;

public  class CustomerBuilder {
    private String firstName;
    private String lastName;
    private String email;
    private String username;
    private String password;
    private LocalDateTime dateOfRegistration;
    private Credit credit;
    private Set<Order> orderSet;
    private Set<Comment> commentSet;

    CustomerBuilder() {
    }

    public static CustomerBuilder builder() {
        return new CustomerBuilder();
    }

    public CustomerBuilder firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public CustomerBuilder lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public CustomerBuilder email(String email) {
        this.email = email;
        return this;
    }

    public CustomerBuilder username(String username) {
        this.username = username;
        return this;
    }

    public CustomerBuilder password(String password) {
        this.password = password;
        return this;
    }

    public CustomerBuilder dateOfRegistration(LocalDateTime dateOfRegistration) {
        this.dateOfRegistration = dateOfRegistration;
        return this;
    }

    public CustomerBuilder credit(Credit credit) {
        this.credit = credit;
        return this;
    }

    public CustomerBuilder orderSet(Set<Order> orderSet) {
        this.orderSet = orderSet;
        return this;
    }

    public CustomerBuilder commentSet(Set<Comment> commentSet) {
        this.commentSet = commentSet;
        return this;
    }

    public Customer build() {
        return new Customer(firstName, lastName, email, username, password, dateOfRegistration, credit, orderSet, commentSet);
    }

    public String toString() {
        return "Customer.CustomerBuilder(firstName=" + this.firstName + ", lastName=" + this.lastName + ", email=" + this.email + ", username=" + this.username + ", password=" + this.password + ", dateOfRegistration=" + this.dateOfRegistration + ", credit=" + this.credit + ", orderSet=" + this.orderSet + ", commentSet=" + this.commentSet + ")";
    }
}