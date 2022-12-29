package com.example.ProjectV2.controller;

import com.example.ProjectV2.dto.Customer.*;
import com.example.ProjectV2.entity.*;
import com.example.ProjectV2.service.*;
import lombok.*;
import org.springframework.web.bind.annotation.*;

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


    @PostMapping("save-customer")
    public void save(@RequestBody Customer customer) {
        customerService.save(customer);
    }


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


    @GetMapping("offers-sorted-by-price")
    public List<SortedOffersDto> sortedOffersByPrices(@RequestParam Long orderId) {
        List<Offer> offerList = offerService.findAllOfferOneOrderByPrice(orderId);
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
    public List<SortedOffersDto> sortedOffersByExpertScore(@RequestParam Long orderId) {
        List<Offer> offerList = offerService.findAllOfferOneOrderByExpertScore(orderId);
        List<SortedOffersDto> resultSortedOffer = new ArrayList<>();
        for (Offer o : offerList) {
            SortedOffersDto offerSortedByExpertScore
                    = new SortedOffersDto(o.getExpert().getUsername(), o.getStartDate()
                    , o.getEndDate(), o.getPrice(), o.getPrice(), o.getRegisterOfferDate());
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


    @PutMapping("pay-credit")
    public void payWithCredit(@RequestBody PayDto payDto) {
        customerService.credit(payDto.getOrderId(), payDto.getAmount());

    }


    @PutMapping("pay-online")
    public void payWithOnline(@RequestBody PayDto payDto) {
        customerService.online(payDto.getOrderId(), payDto.getAmount());
    }


}
