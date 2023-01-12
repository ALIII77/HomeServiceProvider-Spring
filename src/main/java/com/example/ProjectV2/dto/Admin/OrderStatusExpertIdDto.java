package com.example.ProjectV2.dto.Admin;

import com.example.ProjectV2.entity.enums.OrderStatus;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class OrderStatusExpertIdDto {
    private OrderStatus orderStatus;
    private Long expertId;
}
