package com.example.ProjectV2.entity;

import jakarta.persistence.Entity;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Admin extends Person {

    public Admin(String firstName, String lastName, String email, String username, String password, LocalDateTime dateOfRegistration) {
        super(firstName, lastName, email, username, password, dateOfRegistration);
    }

    public Admin(String firstName, String lastName, String email, String username, String password) {
        super(firstName, lastName, email, username, password);
    }

}
