package com.example.ProjectV2.controller;

import com.example.ProjectV2.dto.Admin.ExpertDto;
import com.example.ProjectV2.dto.Admin.SaveExpertWithPictureByAdminDto;
import com.example.ProjectV2.dto.Customer.OrderDto;
import com.example.ProjectV2.dto.Customer.OrderShowDto;
import com.example.ProjectV2.dto.Customer.SaveCustomerDto;
import com.example.ProjectV2.dto.Expert.AddOfferByExpertDto;
import com.example.ProjectV2.dto.Expert.ExpertChangePasswordDto;
import com.example.ProjectV2.dto.Expert.ExpertIdOrderStatusDto;
import com.example.ProjectV2.dto.Expert.ExpertSaveDto;
import com.example.ProjectV2.entity.Customer;
import com.example.ProjectV2.entity.Expert;
import com.example.ProjectV2.entity.Offer;
import com.example.ProjectV2.entity.Order;
import com.example.ProjectV2.entity.enums.OrderStatus;
import com.example.ProjectV2.service.CommentService;
import com.example.ProjectV2.service.ExpertService;
import com.example.ProjectV2.service.OfferService;
import com.example.ProjectV2.service.OrderService;
import com.example.ProjectV2.utils.SendEmail;
import jakarta.annotation.security.PermitAll;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.*;
import org.modelmapper.ModelMapper;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.UnsupportedEncodingException;
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
    private final SendEmail sendEmail;
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
    public List<OrderShowDto> showAllOrderByExpertSubService(@PathVariable Long expertId) {
        List<Order> orderList = expertService.showAllOrderByExpertSubService(expertId);
        return showOrders(orderList);
    }


    @GetMapping("all-order-by-expert-subservice-and-order-status")
    public List<OrderDto> showAllOrderByExpertSubServiceAndOrderStatus
            (@RequestBody ExpertIdOrderStatusDto expertIdOrderStatusDto) {
        List<Order> orderList = expertService.showAllOrderByExpertSubServiceAndOrderStatus
                (expertIdOrderStatusDto.getExpertId(), expertIdOrderStatusDto.getOrderStatus());
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
    public void addOffer(@RequestBody AddOfferByExpertDto addOfferDto) {
        Offer newOffer = new Offer();
        newOffer.setStartDate(addOfferDto.getStartJobDate());
        newOffer.setEndDate(addOfferDto.getEndJobDate());
        newOffer.setPrice(addOfferDto.getPrice());
        offerService.addOffer(newOffer, addOfferDto.getOrderId(), addOfferDto.getOrderId());
    }


    @GetMapping("find-score-by-expert-Id/{expertId}")
    public List<Double> findScoreByExpertId(@PathVariable Long expertId) {
        return commentService.findScoreByExpertId(expertId);
    }

    @GetMapping("expert-order-profile")
    public List<OrderDto> expertOrderProfile(@RequestParam Map<String, String> predicateMap) {
        List<Order> orderList = expertService.expertOrderProfile(predicateMap);
        List<OrderDto> orderDtoList = orderDtoList(orderList);
        return orderDtoList;
    }


    @GetMapping("expert-credit-by-id/{expertId}")
    public double findCreditByCustomerId(@PathVariable Long expertId) {
        return expertService.findCreditByExpertId(expertId);
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
