package com.example.ProjectV2.entity;

import com.example.ProjectV2.base.BaseEntity;
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
@Builder
public class Transaction extends BaseEntity<Long> {

    @ManyToOne
    private Credit credit;

    @CreationTimestamp
    private LocalDateTime date;

    private double amount;


    //Equals And HashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaction that = (Transaction) o;
        return Double.compare(that.amount, amount) == 0 && Objects.equals(credit, that.credit) && Objects.equals(date, that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(credit, date, amount);
    }

    @Builder
    public Transaction(Credit credit, LocalDateTime date, double amount) {
        this.credit = credit;
        this.date = date;
        this.amount = amount;
    }
}
