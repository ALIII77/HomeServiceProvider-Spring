package com.example.ProjectV2.controller;

import com.example.ProjectV2.dto.Expert.AddOfferByExpertDto;
import com.example.ProjectV2.dto.Expert.ExpertChangePasswordDto;
import com.example.ProjectV2.entity.Admin;
import com.example.ProjectV2.entity.Customer;
import com.example.ProjectV2.entity.Expert;
import com.example.ProjectV2.entity.Offer;
import com.example.ProjectV2.entity.builder.OfferBuilder;
import com.example.ProjectV2.service.AdminService;
import com.example.ProjectV2.service.ExpertService;
import com.example.ProjectV2.service.OfferService;
import lombok.*;
import org.springframework.cglib.core.Local;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/v1/expert")
@RequiredArgsConstructor
public class ExpertController {

    public final OfferService offerService;
    public final ExpertService expertService;


    @PutMapping("change-password-expert")
    public void changePassword(@RequestBody ExpertChangePasswordDto changePasswordExpertDTO) {
        expertService.changePassword(changePasswordExpertDTO.getExpert(), changePasswordExpertDTO.getNewPassword());
    }


    @PostMapping("add-offer")
    public void addOffer(@RequestBody AddOfferByExpertDto addOffer) {
        Offer newOffer = new Offer();
        newOffer.setStartDate(addOffer.getStartJobDate());
        newOffer.setEndDate(addOffer.getEndJobDate());
        newOffer.setPrice(addOffer.getPrice());
        offerService.addOffer(newOffer, addOffer.getOrderId());
    }



}
