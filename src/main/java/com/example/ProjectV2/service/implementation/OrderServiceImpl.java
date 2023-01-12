package com.example.ProjectV2.service.implementation;

import com.example.ProjectV2.dto.Admin.ExpertDto;
import com.example.ProjectV2.dto.Admin.HistoryServiceDto;
import com.example.ProjectV2.entity.*;
import com.example.ProjectV2.entity.enums.OrderStatus;
import com.example.ProjectV2.exception.CustomizedIllegalArgumentException;
import com.example.ProjectV2.exception.NotFoundException;
import com.example.ProjectV2.exception.PermissionDeniedException;
import com.example.ProjectV2.repository.OrderRepository;
import com.example.ProjectV2.service.*;
import com.example.ProjectV2.utils.Convertor;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@org.springframework.stereotype.Service
public class OrderServiceImpl implements OrderService {
    private final ApplicationContext applicationContext;
    private final OrderRepository orderRepository;
    private final CustomerService customerService;
    private final SubServiceService subServiceService;
    private final OfferService offerService;

    private final ModelMapper modelMapper;


    @Autowired
    public OrderServiceImpl(ApplicationContext applicationContext
            , OrderRepository orderRepository, CustomerService customerService
            , SubServiceService subServiceService, OfferService offerService, ModelMapper modelMapper) {
        this.applicationContext = applicationContext;
        this.orderRepository = orderRepository;
        this.customerService = customerService;
        this.subServiceService = subServiceService;
        this.offerService = offerService;
        this.modelMapper = modelMapper;
    }

    @Transactional
    @Override
    public void save(@Valid Order order) {
        orderRepository.save(order);
    }

    @Transactional
    @Override
    public void addOrder(String customerUsername, String description, double purposedPrice
            , String address, String subServiceName) {
        Optional<Customer> customerOptional = customerService.findCustomerByUsername(customerUsername);
        Optional<SubService> subServiceOptional = subServiceService.findSubServiceByName(subServiceName);
        if (customerOptional.isEmpty()) {
            throw new NotFoundException("Not found customer to create an order");
        }
        if (subServiceOptional.isEmpty()) {
            throw new NotFoundException("Not found sub service to create an order with customer");
        }
        SubService findSubService = subServiceOptional.get();
        if (purposedPrice < findSubService.getBasePrice()) {
            throw new PermissionDeniedException(
                    "The price of your order must be greater than or equal to the base price of the sub service");
        }
        Customer findCustomer = customerOptional.get();
        Order newOrder = new Order();

        newOrder.setJobDescription(description);
        newOrder.setProposedPrice(purposedPrice);
        newOrder.setAddress(address);
        newOrder.setCustomer(findCustomer);
        newOrder.setSubService(findSubService);
        newOrder.setExecutionDate(LocalDateTime.now());
        orderRepository.save(newOrder);

    }

    @Override
    @Transactional
    public void addOrder(Order order, Long customerId, String subServiceName) {
        Customer findCustomer = customerService.findById(customerId)
                .orElseThrow(() -> new NotFoundException("Not found customer to create an order"));
        SubService findSubService = subServiceService.findSubServiceByName(subServiceName)
                .orElseThrow(() -> new NotFoundException("Not found sub service to create an order with customer"));

        if (order.getProposedPrice() < findSubService.getBasePrice()) {
            throw new CustomizedIllegalArgumentException(
                    "The price of your order must be greater than or equal to the base price of the sub service");
        }
        if (order.getExecutionDate().isBefore(LocalDateTime.now())) {
            throw new CustomizedIllegalArgumentException("order execution time must be after now time");
        }
        order.setSubService(findSubService);
        order.setCustomer(findCustomer);
        orderRepository.save(order);
    }


    @Override
    public Optional<Order> findOrderById(Long id) {
        return orderRepository.findById(id);
    }


    @Transactional
    @Override
    public void changeOrderStatusToStarted(Order order) {
        Order findOrder = orderRepository.findById(order.getId())
                .orElseThrow(() -> new NotFoundException("Not found order with id = " + order.getId()));
        if (findOrder.getOrderStatus() != OrderStatus.COMING_EXPERTS) {
            throw new CustomizedIllegalArgumentException
                    ("To set the status of the order to Started, the order must first be in 'COMING EXPERT' status");
        }
        if (LocalDateTime.now().isAfter(findOrder.getAcceptedOffer().getStartDate())
                && LocalDateTime.now().isBefore(findOrder.getAcceptedOffer().getEndDate())) {
            findOrder.setOrderStatus(OrderStatus.STARTED);
            orderRepository.save(findOrder);
        } else {
            throw new CustomizedIllegalArgumentException("The registered time for the order has not yet arrived");
        }
    }


    @Override
    public void changeOrderStatusToDone(Order order) {
        Order findOrder = orderRepository.findById(order.getId())
                .orElseThrow(() -> new NotFoundException("Not found order with id = " + order.getId()));
        if (findOrder.getOrderStatus() != OrderStatus.STARTED) {
            throw new CustomizedIllegalArgumentException
                    ("the execution of the order must first be 'STARTED' state " +
                            ", then it  will be change status order to 'DONE' state");
        }
        findOrder.setOrderStatus(OrderStatus.DONE);
        applicationContext.getBean(ExpertService.class).setScoreAfterJobEnd(findOrder.getAcceptedOffer().getId());
        orderRepository.save(findOrder);
    }


    @Override
    public void changeOrderStatusToPaid(Order order) {
        Order findOrder = orderRepository.findById(order.getId()).get();
        findOrder.setOrderStatus(OrderStatus.PAID);
        orderRepository.save(findOrder);
    }


    @Override
    public List<Order> showAllOrderByExpertSubService(Long expertId) {
        return orderRepository.showAllOrderByExpertSubService(expertId);
    }


    @Override
    public List<Order> showAllOrdersWaitingOffer(OrderStatus orderStatus) {
        return orderRepository.findAllByOrderStatus(orderStatus);
    }


    @Override
    public List<Order> showAllOrderByExpertSubServiceAndOrderStatus(Long expertId, OrderStatus orderStatus) {
        return orderRepository.showAllOrderByExpertSubServiceAndOrderStatus(expertId, orderStatus);
    }

    @Override
    public List<Order> findAllOrderByDatePeriod(LocalDateTime startDate, LocalDateTime endDate) {
        List<Order> allOrder = orderRepository.findAll();
        List<Order> resultOrder = new ArrayList<>();
        for (Order o : allOrder) {
            if (o.getOrderRegistrationDate().isAfter(startDate) && o.getOrderRegistrationDate().isBefore(endDate)) {
                resultOrder.add(o);
            }
        }
        if (resultOrder.isEmpty()) {
            throw new NotFoundException("Not exists order between start date " + startDate + " and end date " + endDate);
        } else return resultOrder;
    }

    @Override
    public List<Order> findAllByOrderStatus(OrderStatus orderStatus) {
        List<Order> resultOrder = orderRepository.findAllByOrderStatus(orderStatus);
        if (resultOrder.isEmpty()) {
            throw new NotFoundException("Not exists order with order status = " + orderStatus);
        } else return resultOrder;
    }


    @Override
    public List<HistoryServiceDto> historyService (Map<String, String> predicateMap) {

        List<Order> orders = orderRepository.findAll(orderSpecification(predicateMap));
        List<HistoryServiceDto> historyServiceList = new ArrayList<>();
        for (Order o : orders) {

            ExpertDto expertDto =o.getExpert()!=null?modelMapper.map(o.getExpert(),ExpertDto.class):null;
            HistoryServiceDto historyService = new HistoryServiceDto(o,o.getAcceptedOffer(),expertDto);
            historyServiceList.add(historyService);
        }
        return historyServiceList;
    }

    Specification<Order> orderSpecification(Map<String, String> predicateMap) {
        Specification<Order> specification = Specification.where(null);

        for (Map.Entry<String, String> entry : predicateMap.entrySet()) {
            specification = specification.and((orderRoot, cq, cb) ->
                    switch (entry.getKey()) {

                        case "from" ->
                                cb.greaterThan(orderRoot.get(Order_.executionDate), LocalDateTime.parse(entry.getValue()));
                        case "to" ->
                                cb.lessThan(orderRoot.get(Order_.executionDate), LocalDateTime.parse(entry.getValue()));
                        case "orderStatus" -> {
                            OrderStatus orderStatus = getOrderStatus(entry.getValue());
                            yield cb.equal(orderRoot.get(Order_.orderStatus), orderStatus);
                        }
                        case "serviceId" ->
                                cb.equal(orderRoot.join(Order_.subService).join(SubService_.service).get(Service_.id)
                                        , Long.valueOf(entry.getValue()));
                        case "subServiceId" -> cb.equal(orderRoot.join(Order_.subService).get(SubService_.id)
                                , Long.valueOf(entry.getValue()));

                        case "customerId" -> cb.equal(orderRoot.get(Order_.customer).get(Customer_.id), Long.parseLong(entry.getValue()));

                        case "expertId" -> cb.equal(orderRoot.get(Order_.expert).get(Expert_.id), Long.parseLong(entry.getValue()));


                        default -> throw new CustomizedIllegalArgumentException("not match query!");
                    });
        }
        return specification;
    }


    private OrderStatus getOrderStatus(String orderStatus) {
        try {
            return OrderStatus.valueOf(orderStatus);
        } catch (IllegalArgumentException e) {
            throw new CustomizedIllegalArgumentException("order status incorrect!");
        }
    }



    private ExpertDto setExpertDto(Order o) {
        if (o.getExpert() == null) {
            return null;
        }
        return ExpertDto.builder()
                .firstName(o.getExpert().getFirstName())
                .lastName(o.getExpert().getLastName())
                .email(o.getExpert().getEmail())
                .score(o.getExpert().getScore())
                .username(o.getExpert().getUsername())
                .build();
    }


    public List<HistoryServiceDto> totalHistoryOfService(OrderStatus orderStatus, Long expertId) {

        List<HistoryServiceDto> historyServiceList = new ArrayList<>();

        List<Order> orderList = orderRepository.findAllByOrderStatusAndExpertId(orderStatus, expertId);
        if (orderList.isEmpty()) {
            throw new NotFoundException(" not exists order with order status " + orderStatus + " expert id " + expertId);
        }
        for (Order o : orderList) {
            ExpertDto expertDto = ExpertDto.builder()
                    .username(o.getExpert().getUsername())
                    .firstName(o.getExpert().getFirstName())
                    .lastName(o.getExpert().getLastName())
                    .email(o.getExpert().getEmail())
                    .score(o.getExpert().getScore())
                    .build();
            HistoryServiceDto historyServiceDto = HistoryServiceDto.builder()
                    .orderRegisteredDate(o.getOrderRegistrationDate())
                    .orderStatus(o.getOrderStatus())
                    .customerUsername(o.getCustomer().getUsername())
                    .expertDto(expertDto)
                    .startJob(o.getAcceptedOffer().getStartDate())
                    .endJob(o.getAcceptedOffer().getEndDate())
                    .finalPrice(o.getAcceptedOffer().getPrice())
                    .subServiceName(subServiceService.findSubServiceById(o.getSubService().getId()).get().getName())
                    .build();
            historyServiceList.add(historyServiceDto);
        }
        return historyServiceList;
    }


    @Transactional
    @Override
    public List<Order> expertOrderProfile(Map<String, String> predicateMap) {
        List<Order> orderList = orderRepository.findAll(expertDoOrderSpecification(predicateMap));
        return orderList;
    }



    public Specification<Order> expertDoOrderSpecification(Map<String, String> predicateMap) {
        Specification<Order> specification = Specification.where(null);
        for (Map.Entry<String, String> entry : predicateMap.entrySet()) {
            specification = specification.and((orderRoot, cq, cb) ->
                    switch (entry.getKey()) {

                        case "expertId" -> cb.equal(orderRoot.get(Order_.expert).get(Expert_.id), Convertor.toLong(entry.getValue()));

                        case "dateNow" ->
                                cb.greaterThanOrEqualTo(orderRoot.get(Order_.orderRegistrationDate), Convertor.toLocalDateTime(entry.getValue()));

                        case "orderStatus" ->
                                cb.equal(orderRoot.get(Order_.orderStatus), Convertor.toOrderStatusValue(entry.getValue()));

                        default -> throw new CustomizedIllegalArgumentException("not match query!");
                    });
        }
        return specification;

    }


    @Override
    public List<Order> customerOrderProfile(Map<String, String> predicateMap) {
        List<Order> orderList = orderRepository.findAll(customerSendOrderSpecification(predicateMap));
        return orderList;
    }


    public Specification<Order> customerSendOrderSpecification(Map<String, String> predicateMap) {
        Specification<Order> specification = Specification.where(null);
        for (Map.Entry<String, String> entry : predicateMap.entrySet()) {
            specification = specification.and((orderRoot, cq, cb) ->
                    switch (entry.getKey()) {

                        case "customerId" ->
                                cb.equal(orderRoot.join(Order_.customer).get(Customer_.id), Convertor.toLong(entry.getValue()));

                        case "dateNow" ->
                                cb.greaterThanOrEqualTo(orderRoot.get(Order_.orderRegistrationDate), Convertor.toLocalDateTime(entry.getValue()));

                        case "orderStatus" ->
                                cb.equal(orderRoot.get(Order_.orderStatus), Convertor.toOrderStatusValue(entry.getValue()));

                        default -> throw new CustomizedIllegalArgumentException("not match query!");
                    });
        }
        return specification;

    }


}
