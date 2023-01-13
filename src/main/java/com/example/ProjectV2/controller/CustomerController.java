package com.example.ProjectV2.controller;

import com.example.ProjectV2.dto.Customer.*;
import com.example.ProjectV2.entity.*;
import com.example.ProjectV2.exception.CustomizedIllegalArgumentException;
import com.example.ProjectV2.service.*;
import com.example.ProjectV2.utils.Captcha;
import com.example.ProjectV2.utils.SendEmail;
import jakarta.annotation.security.PermitAll;
import jakarta.mail.MessagingException;
import lombok.*;
import org.modelmapper.ModelMapper;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/customer")
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService customerService;
    private final CommentService commentService;
    private final ServiceService serviceService;
    private final SubServiceService subServiceService;
    private final OfferService offerService;
    private final OrderService orderService;
    private final ExpertService expertService;
    private final SendEmail sendEmail;
    private final ModelMapper modelMapper;


    @PutMapping("change-password-customer")
    public void changePassword(@RequestBody CustomerChangePasswordDto changePasswordDTO) {
        customerService.changePassword(changePasswordDTO.getCustomer(), changePasswordDTO.getNewPassword());
    }


    @GetMapping("show-all-Services")
    public List<ServiceDto> showAllServices() {
        List<ServiceDto> serviceList = new ArrayList<>();
        for (Service s : serviceService.findAllServices()) {
            ServiceDto serviceDto = new ServiceDto();
            serviceDto.setName(s.getName());
            serviceDto.setId(s.getId());
            serviceList.add(serviceDto);
        }
        return serviceList;

    }


    @GetMapping("show-service/{serviceName}")
    public ServiceDto showService(@PathVariable String serviceName) {
        Service service = serviceService.findServiceByName(serviceName).get();
        ServiceDto serviceDto = new ServiceDto();
        serviceDto.setName(service.getName());
        serviceDto.setId(service.getId());
        return serviceDto;
    }


    @GetMapping("show-all-sub-service-with-service-name/{serviceName}")
    public List<SubServiceDto> showAllSubServiceWithServiceName(@PathVariable String serviceName) {
        Service service = new Service();
        service.setName(serviceName);
        List<SubService> subServicesByServiceName = subServiceService.findAllSubServicesByService(service);
        List<SubServiceDto> resultSubServiceList = new ArrayList<>();
        for (SubService s : subServicesByServiceName) {
            SubServiceDto subServiceDTO
                    = new SubServiceDto(s.getService().getName(), s.getName(), s.getDescription(), s.getBasePrice());
            resultSubServiceList.add(subServiceDTO);
        }
        return resultSubServiceList;
    }


    @PostMapping("add-order")
    public void addOrder(@RequestBody OrderDto orderDTO) {
        orderService.addOrder(orderDTO.getOrder(), orderDTO.getCustomerId(), orderDTO.getSubServiceName());

    }


    @PutMapping("select-expert")
    public void selectExpert(@RequestBody SelectExpertDto selectExpert) {
        expertService.selectExpert(selectExpert.getOfferId(), selectExpert.getCustomerId());
    }


    @PostMapping("add-comment")
    public void addComment(@RequestBody AddCommentByCustomerDto commentDTO) {
        Comment newComment = new Comment();
        newComment.setText(commentDTO.getText());
        newComment.setScore(commentDTO.getScore());
        commentService.addComment(newComment, commentDTO.getExpertUsername(), commentDTO.getOrderId());
    }

    @GetMapping("offers-sorted-by-price/{orderId}")
    public List<OffersDto> sortedOffersByPrices(@PathVariable Long orderId) {
        List<Offer> offerList = offerService.findAllOfferOneOrderByPrice(orderId);
        List<OffersDto> resultSortedOffer = new ArrayList<>();
        for (Offer o : offerList) {
            OffersDto offersSortedByPrice
                    = new OffersDto(o.getExpert().getUsername(), o.getStartDate()
                    , o.getEndDate(), o.getPrice(), o.getExpert().getScore(), o.getRegisterOfferDate());
            resultSortedOffer.add(offersSortedByPrice);
        }
        return resultSortedOffer;
    }


    @GetMapping("offers-sorted-by-expert-score/{orderId}")
    public List<OffersDto> sortedOffersByExpertScore(@PathVariable Long orderId) {
        List<Offer> offerList = offerService.findAllOfferOneOrderByExpertScore(orderId);
        List<OffersDto> resultSortedOffer = new ArrayList<>();
        for (Offer o : offerList) {
            OffersDto offerSortedByExpertScore
                    = new OffersDto(o.getExpert().getUsername(), o.getStartDate()
                    , o.getEndDate(), o.getPrice(), o.getExpert().getScore(), o.getRegisterOfferDate());
            resultSortedOffer.add(offerSortedByExpertScore);
        }
        return resultSortedOffer;
    }


    @PutMapping("change-order-status-to-started/{orderId}")
    public void changeOrderStatusToStarted(@PathVariable Long orderId) {
        Order order = new Order();
        order.setId(orderId);
        orderService.changeOrderStatusToStarted(order);
    }


    @PutMapping("change-order-status-to-done/{orderId}")
    public void changeOrderStatusToDone(@PathVariable Long orderId) {
        Order order = new Order();
        order.setId(orderId);
        orderService.changeOrderStatusToDone(order);
    }

    @CrossOrigin
    @PutMapping("pay-credit")
    public String payWithCredit(@RequestBody PayDto payDto) {
        customerService.credit(payDto.getOrderId(), payDto.getAmount());
        Captcha captcha = new Captcha(payDto.getCaptcha());
        if (!captcha.isValid()) {
            throw new CustomizedIllegalArgumentException("captcha is not valid");
        }
        if (!payDto.checkCardNumber()) {
            throw new CustomizedIllegalArgumentException("cart number must be 16 digit");
        }
        return "Success!";

    }




    @PutMapping("pay-online")
    public void payWithOnline(@RequestBody PayDto payDto) {
        customerService.online(payDto.getOrderId(), payDto.getAmount());
    }



    @GetMapping("customer-order-profile")
    public List<OrderDto> customerOrderProfile(@RequestParam Map<String, String> predicateMap) {
        List<Order> orderList = customerService.customerOrderProfile(predicateMap);
        List<OrderDto> orderDtoList = orderDtoList(orderList);
        return orderDtoList;
    }





    @GetMapping("customer-credit-by-id/{customerId}")
    public Credit findCreditByCustomerId(@PathVariable Long customerId){
        return customerService.findCreditByCustomerId(customerId);
    }


    private List<OrderDto> orderDtoList(List<Order> orderList) {
        List<OrderDto> orderDtoList = new ArrayList<>();
        for (Order o : orderList) {
            orderDtoList.add(modelMapper.map(o,OrderDto.class));
        }
        return orderDtoList;
    }





}
