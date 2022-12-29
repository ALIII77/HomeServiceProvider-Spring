package com.example.ProjectV2.dto.Customer;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class PayDto {

    private Long orderId;
    private double amount;

}
