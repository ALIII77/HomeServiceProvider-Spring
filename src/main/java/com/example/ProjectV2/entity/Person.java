package com.example.ProjectV2.entity;

import com.example.ProjectV2.base.BaseEntity;
import com.example.ProjectV2.entity.enums.PersonType;
import com.example.ProjectV2.entity.enums.Role;
import jakarta.annotation.Nullable;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.Objects;

@MappedSuperclass
@Setter
@Getter
@ToString
@AllArgsConstructor
public class Person extends BaseEntity<Long>  {

    @Column(nullable = false)
    @NotEmpty(message = "can not be inset space in firstname")
    @NotNull
    private String firstName;
    @Column(nullable = false)
    @NotEmpty(message = "can not be inset space in lastname")
    @NotNull
    private String lastName;

    @Column(unique = true, nullable = false)
    @Email(message = "No match email with pattern")
    @NotEmpty
    @NotBlank(message = "can not be inset space in email")
    private String email;

    @Column(unique = true, nullable = false)
    @NotEmpty
    @NotNull
    @NotBlank(message = "can not be inset space in username")
    @Pattern(regexp = "^[A-Za-z0-9_.]+$", message = "No match username with pattern")
    private String username;

    @Column(nullable = false)
    @NotEmpty
    @NotNull
    @Pattern(regexp = "^[A-Za-z0-9._$%^&*#!@\\-/\\\\]{8,}+$", message = "No match password with pattern")
    private String password;


    @Enumerated(EnumType.STRING)
    private PersonType personType;


    @Enumerated(EnumType.STRING)
    private Role role;



    @Column(name = "verification_code", length = 64)
    private String verificationCode;


    private boolean enabled;

    @CreationTimestamp
    private LocalDateTime dateOfRegistration;

    public Person(String firstName, String lastName, String email, String username, String password, LocalDateTime dateOfRegistration) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.username = username;
        this.password = password;
        this.dateOfRegistration = dateOfRegistration;
    }

    public Person(String firstName, String lastName, String email, String username, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.username = username;
        this.password = password;
    }



    public Person() {
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return Objects.equals(firstName, person.firstName) && Objects.equals(lastName, person.lastName)
                && Objects.equals(email, person.email) && Objects.equals(username, person.username)
                && Objects.equals(password, person.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, email, username, password);
    }


}
