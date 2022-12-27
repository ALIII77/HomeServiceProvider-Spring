package com.example.ProjectV2.dto.Customer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SubServiceDto {
    private String serviceName;
    private String subServiceName;
    private String description;
    private double basePerice;
}
