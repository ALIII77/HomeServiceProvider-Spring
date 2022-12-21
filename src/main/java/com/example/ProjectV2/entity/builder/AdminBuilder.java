package com.example.ProjectV2.entity.builder;

import com.example.ProjectV2.entity.Admin;

import java.time.LocalDateTime;


public  class AdminBuilder {

    private String firstName;
    private String lastName;
    private String email;
    private String username;
    private String password;
    private LocalDateTime dateOfRegistration;

    public AdminBuilder() {
    }

    public static AdminBuilder builder() {
        return new AdminBuilder();
    }

    public AdminBuilder firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public AdminBuilder lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public AdminBuilder email(String email) {
        this.email = email;
        return this;
    }

    public AdminBuilder username(String username) {
        this.username = username;
        return this;
    }

    public AdminBuilder password(String password) {
        this.password = password;
        return this;
    }

    public AdminBuilder dateOfRegistration(LocalDateTime dateOfRegistration) {
        this.dateOfRegistration = dateOfRegistration;
        return this;
    }

    public Admin build() {
        return new Admin(firstName, lastName, email, username, password, dateOfRegistration);
    }

    public String toString() {
        return "Admin.AdminBuilder(firstName=" + this.firstName + ", lastName=" + this.lastName + ", email=" + this.email + ", username=" + this.username + ", password=" + this.password + ", dateOfRegistration=" + this.dateOfRegistration + ")";
    }
}