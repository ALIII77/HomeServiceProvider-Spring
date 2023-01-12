package com.example.ProjectV2.dto.Admin;


import com.example.ProjectV2.dto.Expert.ExpertSaveDto;
import com.example.ProjectV2.entity.Expert;
import com.example.ProjectV2.entity.Offer;
import com.example.ProjectV2.entity.Order;
import com.example.ProjectV2.entity.Transaction;
import com.example.ProjectV2.entity.enums.OrderStatus;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class HistoryServiceDto {
    private LocalDateTime orderRegisteredDate;
    private OrderStatus orderStatus;
    private String customerUsername;
    private ExpertDto expertDto;
    private String subServiceName;
    private double finalPrice;
    private LocalDateTime startJob;
    private LocalDateTime endJob;


    public HistoryServiceDto(Order o, Offer findOffer, ExpertDto expertDto) {

        orderRegisteredDate = o.getOrderRegistrationDate();
        orderStatus = o.getOrderStatus();
        customerUsername = o.getCustomer().getUsername();
        this.expertDto = expertDto;
        subServiceName = o.getSubService().getName();
        if (findOffer!=null){
            finalPrice = findOffer.getPrice();
            startJob = findOffer.getStartDate();
            endJob = findOffer.getEndDate();
        }

    }
}




