package com.example.ProjectV2.dto.Customer;


import com.example.ProjectV2.entity.Order;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class changeOrderStatusDto {
    private Order order;
    private Long offerId;
}
