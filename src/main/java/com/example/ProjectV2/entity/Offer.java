package com.example.ProjectV2.entity;

import com.example.ProjectV2.base.BaseEntity;
import jakarta.annotation.Nullable;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Offer extends BaseEntity<Long> {

    @ManyToOne(cascade = CascadeType.MERGE)
    @ToString.Exclude
    private Expert expert;

    @CreationTimestamp
    private LocalDateTime registerOfferDate;

    @NonNull
    private LocalDateTime startDate;
    @NonNull
    private LocalDateTime endDate;
    @NonNull
    private double price;


    @ManyToOne(cascade = CascadeType.MERGE)
    @ToString.Exclude
    private Order order;

    public Offer(double price, Order order) {
        this.price = price;
        this.order = order;
    }

    public Offer(Expert expert, double price, Order order) {
        this.expert = expert;
        this.price = price;
        this.order = order;
    }


    public Offer(Expert expert, LocalDateTime registerOfferDate, double price, Order order) {
        this.expert = expert;
        this.registerOfferDate = registerOfferDate;
        this.price = price;
        this.order = order;
    }

    public Offer(Expert expert, LocalDateTime startDate, LocalDateTime endDate, double price, Order order) {
        this.expert = expert;
        this.startDate = startDate;
        this.endDate = endDate;
        this.price = price;

        this.order = order;
    }


    //Equals And HashCode


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Offer offer = (Offer) o;
        return Double.compare(offer.price, price) == 0
                && Objects.equals(expert, offer.expert)
                && Objects.equals(order, offer.order);
    }

    @Override
    public int hashCode() {
        return Objects.hash(expert, price, order);
    }
}
