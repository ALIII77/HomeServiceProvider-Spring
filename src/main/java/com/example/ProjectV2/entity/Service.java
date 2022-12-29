package com.example.ProjectV2.entity;

import com.example.ProjectV2.base.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Service extends BaseEntity<Long> {

    @Column(unique = true)
    @NotBlank(message = "cant name attribute is null")
    private String name;

    @OneToMany(mappedBy = "service", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @ToString.Exclude
    private Set<SubService> subServices;


    public Service(String name, Set<SubService> subServices) {
        this.name = name;
        this.subServices = subServices;
    }

    public Service(String name) {
        this.name = name;
    }


    public void addSubService(SubService subService) {
        if (subServices == null) {
            this.subServices = new HashSet<>();
        }
        subServices.add(subService);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Service service = (Service) o;
        return Objects.equals(name, service.name) && Objects.equals(id, service.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, id);
    }
}
