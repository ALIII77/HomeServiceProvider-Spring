package com.example.ProjectV2.controller;

import com.example.ProjectV2.dto.Admin.SaveExpertWithPictureByAdminDto;
import com.example.ProjectV2.dto.Customer.OrderDto;
import com.example.ProjectV2.dto.Expert.AddOfferByExpertDto;
import com.example.ProjectV2.dto.Expert.ExpertChangePasswordDto;
import com.example.ProjectV2.dto.Expert.ExpertSaveDto;
import com.example.ProjectV2.entity.Offer;
import com.example.ProjectV2.entity.Order;
import com.example.ProjectV2.entity.enums.OrderStatus;
import com.example.ProjectV2.service.CommentService;
import com.example.ProjectV2.service.ExpertService;
import com.example.ProjectV2.service.OfferService;
import com.example.ProjectV2.service.OrderService;
import lombok.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/expert")
@RequiredArgsConstructor
public class ExpertController {

    public final OfferService offerService;
    public final ExpertService expertService;
    public final CommentService commentService;

    @PostMapping("save-expert")
    public void save(@RequestBody ExpertSaveDto expertSaveDto) {
        expertService.save(expertSaveDto.getExpert());
    }


    @PostMapping("save-expert-with-picture")
    public void saveExpertWithPicture(@RequestBody SaveExpertWithPictureByAdminDto saveExpertWithPictureDTO) {
        if (expertService.findExpertById(saveExpertWithPictureDTO.getExpertId()).isEmpty()) {
            System.out.println("Not found expert to add avatar picture!");
        }
        expertService.findExpertById(saveExpertWithPictureDTO.getExpert().getId());
        expertService.saveExpertWithPicture(saveExpertWithPictureDTO.getExpert(), saveExpertWithPictureDTO.getPictureFile());
    }


    @GetMapping("show-all-order-waiting-offer/{orderStatus}")
    public List<OrderDto>showAllOrderWaitingOffer(@PathVariable OrderStatus orderStatus){
        List<Order> orderList = expertService.showAllOrdersWaitingOffer(orderStatus);
        return orderDtoList(orderList);
    }



    @GetMapping("show-all-order-by-expert-sub-service/{expertId}")
    public List<OrderDto> showAllOrderByExpertSubService(@PathVariable Long expertId) {
        List<Order> orderList = expertService.showAllOrderByExpertSubService(expertId);
        return orderDtoList(orderList);
    }


    @PutMapping("save-expert-picture/{username}")
    public void setExpertImage(@RequestBody MultipartFile image, @PathVariable String username) {
        expertService.setExpertImage(username, image);
    }


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
        offerService.addOffer(newOffer, addOffer.getOrderId(), addOffer.getOrderId());
    }


    @GetMapping("find-score-by-xper-Id/{expertId}")
    public List<double>findScoreByExpertId(@PathVariable Long expertId){
       return commentService.findScoreByExpertId(expertId);
    }


    private List<OrderDto> orderDtoList (List<Order> orderList){
        List<OrderDto> orderDtoList = new ArrayList<>();
        for (Order o : orderList) {
            OrderDto orderDto = OrderDto.builder()
                    .address(o.getAddress())
                    .description(o.getJobDescription())
                    .executionDate(o.getExecutionDate())
                    .subServiceName(o.getSubService().getName())
                    .proposedPrice(o.getProposedPrice())
                    .build();
            orderDtoList.add(orderDto);
        }
        return orderDtoList;

    }


}
