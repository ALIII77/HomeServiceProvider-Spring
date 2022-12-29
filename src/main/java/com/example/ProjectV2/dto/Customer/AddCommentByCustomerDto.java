package com.example.ProjectV2.dto.Customer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AddCommentByCustomerDto {
    private String text;
    private double score;
    private String expertUsername;
    private Long orderId;
}
