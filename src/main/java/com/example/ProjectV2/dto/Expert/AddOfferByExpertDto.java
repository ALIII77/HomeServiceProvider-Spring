package com.example.ProjectV2.dto.Expert;

import com.example.ProjectV2.entity.Expert;
import com.example.ProjectV2.entity.Offer;
import com.example.ProjectV2.entity.Order;
import com.example.ProjectV2.entity.builder.OfferBuilder;
import com.example.ProjectV2.repository.OfferRepository;
import com.example.ProjectV2.service.OrderService;
import com.example.ProjectV2.service.implementation.OrderServiceImpl;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AddOfferByExpertDto {

    private LocalDateTime startJobDate;
    private LocalDateTime endJobDate;
    private double price;
    private Long orderId;
    private Long expertId;

    public Offer getOffer(){
        Offer offer = new Offer();
        Order order = new Order();
        order.setId(orderId);
        offer.setStartDate(startJobDate);
        offer.setEndDate(endJobDate);
        offer.setPrice(price);
        offer.setOrder(order);

        Expert expert = new Expert();
        expert.setId(expertId);

        offer.setExpert(expert);

        return offer;
    }
}
