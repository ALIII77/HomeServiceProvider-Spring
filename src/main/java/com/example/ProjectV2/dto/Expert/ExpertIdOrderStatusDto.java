package com.example.ProjectV2.dto.Expert;

import com.example.ProjectV2.entity.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ExpertIdOrderStatusDto
{
    private Long expertId;
    private OrderStatus orderStatus;
}
