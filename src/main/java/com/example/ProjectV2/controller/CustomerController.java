package com.example.ProjectV2.controller;

import com.example.ProjectV2.dto.Customer.*;
import com.example.ProjectV2.entity.*;
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
    public void changePassword(@RequestBody CustomerChangePasswordDto changePasswordDTO) {
        customerService.changePassword(changePasswordDTO.getCustomer(), changePasswordDTO.getNewPassword());
    }


    @GetMapping("show-all-Services")
    public List<Service> showAllServices() {
        return serviceService.findAllServices();
    }


    @GetMapping("show-all-sub-service-with-service-name")
    public List<SubServiceDto> showAllSubServiceWithServiceName(@RequestParam String serviceName) {
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


    @PostMapping("add-comment")
    public void addComment(@RequestBody AddCommentByCustomerDto commentDTO) {
        Comment newComment = new Comment();
        newComment.setText(commentDTO.getText());
        newComment.setScore(commentDTO.getScore());
        commentService.addComment(newComment, commentDTO.getOrderId());

    }


    @GetMapping("offers-sorted-by-price")
    public List<SortedOffersDto> sortedOffersByPrices(@RequestParam Long orderId) {                             //check
        List<Offer> offerList = offerService.findAllOfferOneOrderByExpertScore(orderId);
        List<SortedOffersDto> resultSortedOffer = new ArrayList<>();
        for (Offer o : offerList) {
            SortedOffersDto offersSortedByPrice
                    = new SortedOffersDto(o.getExpert().getUsername(), o.getStartDate()
                    , o.getEndDate(), o.getPrice(), o.getExpert().getScore(), o.getRegisterOfferDate());
            resultSortedOffer.add(offersSortedByPrice);
        }
        return resultSortedOffer;
    }


    @GetMapping("offers-sorted-by-expert-score")
    public List<SortedOffersDto> sortedOffersByExpertScore(@RequestParam Long orderId, Long expertId) {          //check
        List<Offer> offerList = offerService.findOfferByOrderIdAndExpertId(orderId, expertId).stream().toList();
        List<SortedOffersDto> resultSortedOffer = new ArrayList<>();
        for (Offer o : offerList) {
            SortedOffersDto offerSortedByExpertScore
                    = new SortedOffersDto(o.getExpert().getUsername(), o.getStartDate()
                    , o.getEndDate(), o.getPrice(), o.getPrice(), o.getRegisterOfferDate());
            resultSortedOffer.add(offerSortedByExpertScore);
        }
        return resultSortedOffer;
    }


    @PutMapping("add-order")
    public void addOrder(@RequestBody OrderDto orderDTO) {
        orderService.addOrder(orderDTO.getOrder(), orderDTO.getCustomerId(), orderDTO.getSubServiceName());

    }


    @PutMapping("select-expert")
    public void selectExpert(@RequestBody SelectExpertDto selectExpert) {
        expertService.selectExpert(selectExpert.getOfferId(), selectExpert.getOrderId());
    }


    @PutMapping("change-order-status-to-started")
    public void changeOrderStatusToStarted(@RequestParam Long orderId) {                                        //check
        Order order = new Order();
        order.setId(orderId);
        orderService.changeOrderStatusToStarted(order);
    }


    @PutMapping("change-order-status-to-done")
    public void changeOrderStatusToDone(@RequestParam Long orderId) {                               //check
        Order order = new Order();
        order.setId(orderId);
        orderService.changeOrderStatusToDone(order);
    }




}
