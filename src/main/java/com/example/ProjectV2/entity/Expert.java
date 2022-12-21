package com.example.ProjectV2.entity;

import com.example.ProjectV2.enums.ExpertStatus;
import com.example.ProjectV2.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Expert extends Person {

    @ManyToMany(mappedBy = "expertSet", fetch = FetchType.EAGER)
    @ToString.Exclude
    private Set<SubService> subServiceSet;

    @OneToMany(mappedBy = "expert", cascade = CascadeType.REMOVE)
    @ToString.Exclude
    private Set<Offer> offerSet;

    @OneToMany(mappedBy = "expert", cascade = CascadeType.REMOVE)
    @ToString.Exclude
    private Set<Comment> commentSet;

    @Enumerated(EnumType.STRING)
    private ExpertStatus expertStatus;

    @OneToMany(mappedBy = "expert")
    @ToString.Exclude
    private Set<Order> orderSet;

    @OneToOne(cascade = {CascadeType.REMOVE, CascadeType.PERSIST})
    @ToString.Exclude
    private Credit credit;
    @ColumnDefault("0")
    @Column(nullable = false)
    private double score; //average score
    //@Lob
    @ToString.Exclude
    private byte[] image;

    public Expert(String firstName, String lastName, String email, String username, String password
            , byte[] image) {
        super(firstName, lastName, email, username, password);
        this.image = image;
    }


    public void addOffer(Offer offer) {
        if (offerSet == null) {
            this.offerSet = new HashSet<>();
        }
        offerSet.add(offer);
    }

    public void addComment(Comment comment) {
        if (commentSet == null) {
            this.commentSet = new HashSet<>();
        }
        commentSet.add(comment);
    }


    public Expert(String firstName, String lastName, String email, String username, String password,
                  LocalDateTime dateOfRegistration, Set<SubService> subServiceSet, Set<Offer> offerSet,
                  Set<Comment> commentSet, ExpertStatus expertStatus, Set<Order> orderSet, Credit credit,
                  double score, byte[] image) {
        super(firstName, lastName, email, username, password, dateOfRegistration);
        this.subServiceSet = subServiceSet;
        this.offerSet = offerSet;
        this.commentSet = commentSet;
        this.expertStatus = expertStatus;
        this.orderSet = orderSet;
        this.credit = credit;
        this.score = score;
        this.image = image;
    }

    @PrePersist
    private void prePersist() {
        expertStatus = ExpertStatus.NEW;
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
