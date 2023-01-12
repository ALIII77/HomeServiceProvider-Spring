package com.example.ProjectV2.entity;

import com.example.ProjectV2.entity.enums.ExpertStatus;
import com.example.ProjectV2.entity.enums.PersonType;
import com.example.ProjectV2.entity.enums.Role;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Expert extends Person implements UserDetails {

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
    private double score;


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
        setPersonType(PersonType.EXPERT);
        setRole(Role.ROLE_EXPERT);
        setEnabled(false);
    }


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




    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(getRole().name()));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }


}
