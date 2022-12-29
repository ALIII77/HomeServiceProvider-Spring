package com.example.ProjectV2.entity;

import com.example.ProjectV2.entity.enums.PersonType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString(callSuper = true)
public class Customer extends Person {


    @OneToOne(cascade = {CascadeType.REMOVE, CascadeType.PERSIST})
    @ToString.Exclude
    private Credit credit;

    @ToString.Exclude
    @OneToMany(mappedBy = "customer", cascade = CascadeType.REMOVE)
    private Set<Order> orderSet;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.REMOVE)
    @ToString.Exclude
    private Set<Comment> commentSet;

    public Customer(String firstName, String lastName, String email, String username, String password) {
        super(firstName, lastName, email, username, password);
    }


    public void addOrder(Order order) {
        if (orderSet == null) {
            this.orderSet = new HashSet<>();
        }
        orderSet.add(order);
    }

    public void addComment(Comment comment) {
        if (commentSet == null) {
            this.commentSet = new HashSet<>();
        }
        commentSet.add(comment);
    }

    public Customer(String firstName, String lastName, String email, String username, String password
            , LocalDateTime dateOfRegistration, Credit credit, Set<Order> orderSet, Set<Comment> commentSet) {
        super(firstName, lastName, email, username, password, dateOfRegistration);
        this.credit = credit;
        this.orderSet = orderSet;
        this.commentSet = commentSet;
    }



    @PrePersist
    public void prePersist(){
        setPersonType(PersonType.CUSTOMER);
    }




    //Equals And HashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode());
    }
}
