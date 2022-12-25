package com.example.ProjectV2.controller;

import com.example.ProjectV2.entity.*;
import com.example.ProjectV2.entity.builder.OrderBuilder;
import com.example.ProjectV2.service.*;
import lombok.*;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/customer")
@RequiredArgsConstructor
public class CustomerController {

    public final CustomerService customerService;
    public final CommentService commentService;
    public final ServiceService serviceService;
    public final SubServiceService subServiceService;
    public final OfferService offerService;
    public final OrderService orderService;
    public final ExpertService expertService;


    @PutMapping("change-password-customer")
    public void changePassword(@RequestBody ChangePasswordCustomerDTO changePasswordDTO) {
        customerService.changePassword(changePasswordDTO.getCustomer(), changePasswordDTO.getNewPassword());
    }


    @GetMapping("show-all-Services")
    public List<Service> showAllServices() {
        return serviceService.findAll();
    }


    @GetMapping("show-all-sub-service-with-service-name")
    public List<SubServiceDTO> showAllSubServiceWithServiceName(@RequestParam String serviceName) {
        Service service = new Service();
        service.setName(serviceName);
        List<SubService> subServicesByServiceName = subServiceService.findAllSubServicesByService(service);
        List<SubServiceDTO> resultSubServiceList = new ArrayList<>();
        for (SubService s : subServicesByServiceName) {
            SubServiceDTO subServiceDTO
                    = new SubServiceDTO(s.getService().getName(), s.getName(), s.getDescription(), s.getBasePrice());
            resultSubServiceList.add(subServiceDTO);
        }
        return resultSubServiceList;
    }


    @PostMapping("add-comment")
    public void addComment(@RequestBody CommentDTO commentDTO) {
        Comment newComment = new Comment();
        newComment.setText(commentDTO.getText());
        newComment.setScore(commentDTO.getScore());
        commentService.addComment(newComment, commentDTO.getOrderId());

    }


    @GetMapping("offers-sorted-by-price")
    public List<SortedOffersDTO> sortedOffersByPrices(@RequestParam Long orderId) {                             //check
        List<Offer> offerList = offerService.findAllOfferOneOrderByExpertScore(orderId);
        List<SortedOffersDTO> resultSortedOffer = new ArrayList<>();
        for (Offer o : offerList) {
            SortedOffersDTO offersSortedByPrice
                    = new SortedOffersDTO(o.getExpert().getUsername(), o.getStartDate()
                    , o.getEndDate(), o.getPrice(), o.getExpert().getScore(), o.getRegisterOfferDate());
            resultSortedOffer.add(offersSortedByPrice);
        }
        return resultSortedOffer;
    }


    @GetMapping("offers-sorted-by-expert-score")
    public List<SortedOffersDTO> sortedOffersByExpertScore(@RequestParam Long orderId, Long expertId) {          //check
        List<Offer> offerList = offerService.findOfferByOrderIdAndExpertId(orderId, expertId).stream().toList();
        List<SortedOffersDTO> resultSortedOffer = new ArrayList<>();
        for (Offer o : offerList) {
            SortedOffersDTO offerSortedByExpertScore
                    = new SortedOffersDTO(o.getExpert().getUsername(), o.getStartDate()
                    , o.getEndDate(), o.getPrice(), o.getPrice(), o.getRegisterOfferDate());
            resultSortedOffer.add(offerSortedByExpertScore);
        }
        return resultSortedOffer;
    }


    @PutMapping("add-order")
    public void addOrder(@RequestBody OrderDTO orderDTO) {
        orderService.addOrder(orderDTO.getOrder(), orderDTO.getCustomerId(), orderDTO.getSubServiceName());

    }


    @PutMapping("select-expert")
    public void selectExpert(@RequestBody SelectExpertDTO selectExpert) {
        expertService.selectExpert(selectExpert.getOfferId(), selectExpert.getOrderId());
    }


    @PutMapping("change-order-status-to-started")
    public void changeOrderStatusToStarted(@RequestParam Long orderId) {                                        //check
        Order order = new Order();
        order.setId(orderId);
        orderService.changeOrderStatusToStarted(order);
    }


    @PutMapping("change-order-status-to-done")
    public void changeOrderStatusToDone(@RequestParam Long orderId, Long offerId) {                               //check
        Order order = new Order();
        order.setId(orderId);
        Offer offer = new Offer();
        offer.setId(offerId);
        orderService.changeOrderStatusToDone(order, offer);
    }


    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    static class ChangePasswordCustomerDTO {
        private Customer customer;
        private String newPassword;
    }


    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    static class CommentDTO {
        private String text;
        private double score;
        private Long orderId;
    }


    //SELECT EXPERT DTO CLASS
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    static class SelectExpertDTO {
        private Long offerId;
        private Long orderId;
    }


    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    static class SubServiceDTO {
        private String serviceName;
        private String subServiceName;
        private String description;
        private double basePerice;
    }


    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    static class SortedOffersDTO {
        private String expertUsername;
        private LocalDateTime startJobDate;
        private LocalDateTime endJobDate;
        private double price;
        private double score;
        private LocalDateTime registeredOfferDate;
    }


    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    static class OrderDTO {
        private Order order;
        private Long customerId;
        private String subServiceName;
    }


    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    static class changeOrderStatusDTO {
        private Order order;
        private Long offerId;
    }


}
