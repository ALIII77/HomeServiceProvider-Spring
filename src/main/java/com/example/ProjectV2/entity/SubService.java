package com.example.ProjectV2.entity;

import com.example.ProjectV2.base.BaseEntity;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
public class SubService extends BaseEntity<Long> {

    @Column(unique = true, nullable = false)
    @NotBlank(message = "cant name attribute is null")
    private String name;

    @ManyToOne
    @ToString.Exclude
    private Service service;

    @Column(columnDefinition = "text")
    @NotEmpty
    private String description;

    @Nullable
    private double basePrice;

    @ManyToMany
    @JoinTable(name = "expert_subservice")
    @ToString.Exclude
    private Set<Expert> expertSet;

    @OneToMany(mappedBy = "subService")
    @ToString.Exclude
    private Set<Order> orderSet;


    public SubService(String name, String description, double basePrice) {
        this.name = name;
        this.description = description;
        this.basePrice = basePrice;
    }


    public void addExpert(Expert expert) {
        if (expertSet == null) {
            this.expertSet = new HashSet<>();
        }
        expertSet.add(expert);
    }

    public void addOrder(Order order) {
        if (orderSet == null) {
            this.orderSet = new HashSet<>();
        }
        orderSet.add(order);
    }

    public SubService(String name, Service service, String description, double basePrice, Set<Expert> expertSet, Set<Order> orderSet) {
        this.name = name;
        this.service = service;
        this.description = description;
        this.basePrice = basePrice;
        this.expertSet = expertSet;
        this.orderSet = orderSet;
    }

    //Equals And HashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SubService that = (SubService) o;
        return Objects.equals(basePrice, that.basePrice) && Objects.equals(name, that.name)
                && Objects.equals(service, that.service) && Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, service, description, basePrice);
    }
}
