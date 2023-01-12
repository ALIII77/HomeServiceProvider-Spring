package com.example.ProjectV2.entity;

import com.example.ProjectV2.base.BaseEntity;
import com.example.ProjectV2.entity.enums.OrderStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
@Table(name = "order_table")
public class Order extends BaseEntity<Long> {

    @CreationTimestamp
    private LocalDateTime orderRegistrationDate;

    @ManyToOne
    private Customer customer;

    @ManyToOne(cascade = CascadeType.MERGE)
    private Expert expert;

    @NotNull
    private String jobDescription;

    @NotNull
    private LocalDateTime executionDate;


    private double delay;

    @NotNull
    private String address;

    @NotNull
    private double proposedPrice;


    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @OneToMany(mappedBy = "order", cascade = CascadeType.REMOVE)
    @ToString.Exclude
    private Set<Offer> offerSet;

    @OneToOne
    private Offer acceptedOffer;

    @ManyToOne
    @ToString.Exclude
    private SubService subService;

    @OneToOne()
    @ToString.Exclude
    private Comment comment;


    @OneToOne
    @ToString.Exclude
    private Transaction transaction;


    public Order(String jobDescription, LocalDateTime executionDate, String address, double proposedPrice, OrderStatus orderStatus) {
        this.jobDescription = jobDescription;
        this.executionDate = executionDate;
        this.address = address;
        this.proposedPrice = proposedPrice;
        this.orderStatus = orderStatus;
    }

    public Order(String jobDescription, LocalDateTime executionDate, String address, double proposedPrice, SubService subService) {
        this.jobDescription = jobDescription;
        this.executionDate = executionDate;
        this.address = address;
        this.proposedPrice = proposedPrice;
        this.subService = subService;
    }

    public Order(String jobDescription, String address, double proposedPrice) {
        this.jobDescription = jobDescription;
        this.address = address;
        this.proposedPrice = proposedPrice;
    }

    public void addOffer(Offer offer) {
        if (offerSet == null) {
            this.offerSet = new HashSet<>();
        }
        offerSet.add(offer);
    }


    public Order(Customer customer, String jobDescription, LocalDateTime executionDate, String address, double proposedPrice, SubService subService) {
        this.customer = customer;
        this.jobDescription = jobDescription;
        this.executionDate = executionDate;
        this.address = address;
        this.proposedPrice = proposedPrice;
        this.subService = subService;
    }

    public Order(Customer customer, Expert expert, String jobDescription, LocalDateTime executionDate
            , String address, double proposedPrice, OrderStatus orderStatus, Set<Offer> offerSet, SubService subService, Comment comment) {
        this.customer = customer;
        this.expert = expert;
        this.jobDescription = jobDescription;
        this.executionDate = executionDate;
        this.address = address;
        this.proposedPrice = proposedPrice;
        this.orderStatus = orderStatus;
        this.offerSet = offerSet;
        this.subService = subService;
        this.comment = comment;
    }


    @PrePersist
    private void prePersist() {
        orderStatus = OrderStatus.WAITING_FOR_SEND_OFFER_EXPERTS;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Double.compare(order.proposedPrice, proposedPrice) == 0 && Objects.equals(customer, order.customer)
                && Objects.equals(expert, order.expert) && Objects.equals(jobDescription, order.jobDescription)
                && Objects.equals(address, order.address)
                && orderStatus == order.orderStatus && Objects.equals(subService, order.subService);
    }

    @Override
    public int hashCode() {
        return Objects.hash(customer, expert, jobDescription, executionDate, address, proposedPrice, orderStatus, subService);
    }
}
