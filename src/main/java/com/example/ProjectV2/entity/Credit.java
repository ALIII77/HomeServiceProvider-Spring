package com.example.ProjectV2.entity;

import com.example.ProjectV2.base.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Credit extends BaseEntity<Long> {

    @ColumnDefault("0")
    @PositiveOrZero(message = " Amount must be positive value")
    private double amount;


    public Credit(double amount) {
        this.amount = amount;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Credit credit = (Credit) o;
        return Double.compare(credit.amount, amount) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(amount);
    }
}
