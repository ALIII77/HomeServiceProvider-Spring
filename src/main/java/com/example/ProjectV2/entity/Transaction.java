package com.example.ProjectV2.entity;

import com.example.ProjectV2.base.BaseEntity;
import com.example.ProjectV2.entity.enums.TransactionType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Transaction extends BaseEntity<Long> {

    @ManyToOne
    private Credit sourceCredit;

    @ManyToOne
    private Credit destinationCredit;

    @CreationTimestamp
    private LocalDateTime date;

    private double amount;

    @OneToOne(mappedBy = "transaction")
    private Order order;


    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;


    public Transaction(Credit sourceCredit, Credit destinationCredit, double amount, Order order, TransactionType transactionType) {
        this.sourceCredit = sourceCredit;
        this.destinationCredit = destinationCredit;
        this.amount = amount;
        this.order = order;
        this.transactionType = transactionType;
    }


    public Transaction(Credit destinationCredit, double amount, Order order, TransactionType transactionType) {
        this.destinationCredit = destinationCredit;
        this.amount = amount;
        this.order = order;
        this.transactionType = transactionType;
    }

    //Equals And HashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaction that = (Transaction) o;
        return Double.compare(that.amount, amount) == 0 && Objects.equals(sourceCredit, that.sourceCredit) && Objects.equals(destinationCredit, that.destinationCredit) && Objects.equals(date, that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sourceCredit, destinationCredit, date, amount);
    }


}
