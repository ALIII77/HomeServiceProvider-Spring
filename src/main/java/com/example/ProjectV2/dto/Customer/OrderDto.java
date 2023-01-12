package com.example.ProjectV2.dto.Customer;

import com.example.ProjectV2.entity.Order;
import com.example.ProjectV2.entity.builder.OrderBuilder;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class OrderDto {
    private String description;
    private String address;
    private double proposedPrice;
    private LocalDateTime executionDate;
    private Long customerId;
    private String subServiceName;

    @JsonIgnore
    public Order getOrder(){
        Order order = new OrderBuilder()
                .jobDescription(description)
                .address(address)
                .proposedPrice(proposedPrice)
                .executionDate(executionDate)
                .build();
        return order;
    }
}
