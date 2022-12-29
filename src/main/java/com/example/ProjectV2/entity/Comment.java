package com.example.ProjectV2.entity;


import com.example.ProjectV2.base.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.util.Objects;


@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Comment extends BaseEntity<Long> {


    @ManyToOne
    @ToString.Exclude
    private Customer customer;
    @ManyToOne
    @ToString.Exclude
    private Expert expert;

    private String text;

    @Column(nullable = false)
    @Positive(message = "score can be positive value")
    @Max(value = 5, message = "max score value is 5")
    private double score;

    @OneToOne(mappedBy = "comment")
    @ToString.Exclude
    private Order order;

    public Comment(String text, int score) {
        this.text = text;
        this.score = score;
    }

    public Comment(Customer customer, Expert expert, String text, double score, Order order) {
        this.customer = customer;
        this.expert = expert;
        this.text = text;
        this.score = score;
        this.order = order;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Comment comment = (Comment) o;
        return Double.compare(comment.score, score) == 0
                && Objects.equals(text, comment.text)
                && Objects.equals(id, comment.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash( text, score, order);
    }
}
