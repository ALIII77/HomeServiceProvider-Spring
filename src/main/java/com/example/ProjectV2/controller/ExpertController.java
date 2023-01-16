package com.example.ProjectV2.controller;


import com.example.ProjectV2.dto.Admin.SaveExpertWithPictureByAdminDto;
import com.example.ProjectV2.dto.Customer.OrderDto;
import com.example.ProjectV2.dto.Customer.OrderShowDto;

import com.example.ProjectV2.dto.Expert.AddOfferByExpertDto;
import com.example.ProjectV2.dto.Expert.ExpertChangePasswordDto;
import com.example.ProjectV2.dto.Expert.ExpertIdOrderStatusDto;

import com.example.ProjectV2.entity.Expert;
import com.example.ProjectV2.entity.Offer;
import com.example.ProjectV2.entity.Order;
import com.example.ProjectV2.entity.enums.OrderStatus;
import com.example.ProjectV2.service.CommentService;
import com.example.ProjectV2.service.ExpertService;
import com.example.ProjectV2.service.OfferService;
import lombok.*;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/expert")
@RequiredArgsConstructor
public class ExpertController {

    private final OfferService offerService;
    private final ExpertService expertService;
    private final CommentService commentService;
    private final ModelMapper modelMapper;



    @PostMapping("save-expert-with-picture")
    public void saveExpertWithPicture(@RequestBody SaveExpertWithPictureByAdminDto saveExpertWithPictureDTO) {
        if (expertService.findExpertById(saveExpertWithPictureDTO.getExpertId()).isEmpty()) {
            System.out.println("Not found expert to add avatar picture!");
        }
        expertService.findExpertById(saveExpertWithPictureDTO.getExpert().getId());
        expertService.saveExpertWithPicture(saveExpertWithPictureDTO.getExpert(), saveExpertWithPictureDTO.getPictureFile());
    }


    @GetMapping("show-all-order-waiting-offer/{orderStatus}")
    public List<OrderDto> showAllOrderWaitingOffer(@PathVariable OrderStatus orderStatus) {
        List<Order> orderList = expertService.showAllOrdersWaitingOffer(orderStatus);
        return orderDtoList(orderList);
    }


    @GetMapping("show-all-order-by-expert-sub-service/{expertId}")
    public List<OrderShowDto> showAllOrderByExpertSubService(Authentication authentication) {
        Expert expert = (Expert) authentication.getPrincipal();
        List<Order> orderList = expertService.showAllOrderByExpertSubService(expert.getId());
        return showOrders(orderList);
    }


    @GetMapping("all-order-by-expert-subservice-and-order-status")
    public List<OrderDto> showAllOrderByExpertSubServiceAndOrderStatus
            (@RequestBody ExpertIdOrderStatusDto expertIdOrderStatusDto) {
        List<Order> orderList = expertService.showAllOrderByExpertSubServiceAndOrderStatus
                (expertIdOrderStatusDto.getExpertId(), expertIdOrderStatusDto.getOrderStatus());
        return orderDtoList(orderList);
    }


    @PutMapping("save-expert-picture")
    public void setExpertImage(@RequestBody MultipartFile image,Authentication authentication) {
        Expert expert = (Expert)authentication.getPrincipal();
        expertService.setExpertImage(expert.getUsername(), image);
    }


    @PutMapping("change-password-expert")
    public void changePassword(@RequestBody ExpertChangePasswordDto changePasswordExpertDTO,Authentication authentication) {
        Expert expert = (Expert)authentication.getPrincipal();
        expertService.changePassword(expert.getUsername()
                ,changePasswordExpertDTO.getOldPassword(),changePasswordExpertDTO.getNewPassword() );
    }


    @PostMapping("add-offer")
    public void addOffer(@RequestBody AddOfferByExpertDto addOfferDto , Authentication authentication) {
        Expert expert = (Expert) authentication.getPrincipal();
        Offer newOffer = new Offer();
        newOffer.setStartDate(addOfferDto.getStartJobDate());
        newOffer.setEndDate(addOfferDto.getEndJobDate());
        newOffer.setPrice(addOfferDto.getPrice());
        offerService.addOffer(newOffer, expert.getId(), addOfferDto.getOrderId());
    }


    @GetMapping("find-score-by-expert-Id")
    public List<Double> findScoreByExpertId(Authentication authentication) {
        Expert expert = (Expert)authentication.getPrincipal();
        return commentService.findScoreByExpertId(expert.getId());
    }


    @GetMapping("expert-order-profile")
    public List<OrderDto> expertOrderProfile(@RequestParam Map<String, String> predicateMap, Authentication authentication) {
        Expert expert = (Expert)authentication.getPrincipal();
        predicateMap.put("expertId",String.valueOf(expert.getId()));
        List<Order> orderList = expertService.expertOrderProfile(predicateMap);
        List<OrderDto> orderDtoList = orderDtoList(orderList);
        return orderDtoList;
    }


    @GetMapping("expert-credit-by-id")
    public double findCreditByCustomerId(Authentication authentication) {
        Expert expert = (Expert)authentication.getPrincipal();
        return expertService.findCreditByExpertId(expert.getId());
    }




    private List<OrderDto> orderDtoList(List<Order> orderList) {
        List<OrderDto> orderDtoList = new ArrayList<>();
        for (Order o : orderList) {
            orderDtoList.add(modelMapper.map(o,OrderDto.class));
        }
        return orderDtoList;
    }



    private List<OrderShowDto> showOrders (List<Order> orderList) {
        List<OrderShowDto> orderDtoList = new ArrayList<>();
        for (Order o : orderList) {
            orderDtoList.add(modelMapper.map(o,OrderShowDto.class));
        }
        return orderDtoList;
    }



}
