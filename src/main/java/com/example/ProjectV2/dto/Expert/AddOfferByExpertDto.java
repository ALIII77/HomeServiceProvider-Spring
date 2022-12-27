package com.example.ProjectV2.dto.Expert;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AddOfferByExpertDto {
    private Long orderId;
    private LocalDateTime startJobDate;
    private LocalDateTime endJobDate;
    private double price;
}
