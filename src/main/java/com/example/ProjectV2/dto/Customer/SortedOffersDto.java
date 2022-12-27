package com.example.ProjectV2.dto.Customer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SortedOffersDto {
    private String expertUsername;
    private LocalDateTime startJobDate;
    private LocalDateTime endJobDate;
    private double price;
    private double score;
    private LocalDateTime registeredOfferDate;
}
