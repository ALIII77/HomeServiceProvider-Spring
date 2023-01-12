package com.example.ProjectV2.service.implementation;

import com.example.ProjectV2.entity.Expert;
import com.example.ProjectV2.entity.Offer;
import com.example.ProjectV2.entity.Order;
import com.example.ProjectV2.entity.enums.ExpertStatus;
import com.example.ProjectV2.exception.CustomizedIllegalArgumentException;
import com.example.ProjectV2.exception.NotFoundException;
import com.example.ProjectV2.exception.PermissionDeniedException;
import com.example.ProjectV2.repository.OfferRepository;
import com.example.ProjectV2.service.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@org.springframework.stereotype.Service
public class OfferServiceImpl implements OfferService {

    private final ApplicationContext applicationContext;
    private final OfferRepository offerRepository;


    @Autowired
    public OfferServiceImpl(ApplicationContext applicationContext, OfferRepository offerRepository) {
        this.applicationContext = applicationContext;
        this.offerRepository = offerRepository;
    }

    @Transactional
    @Override
    public void save(@Valid Offer offer) {
        offerRepository.save(offer);
    }

    @Transactional
    @Override
    public void addOffer(Offer offer, Long expertId,Long orderId) {

        ExpertService expertService = applicationContext.getBean(ExpertService.class);
        OrderService orderService = applicationContext.getBean(OrderService.class);
        Expert findExpert = expertService.findExpertById(expertId)
                .orElseThrow(() -> new NotFoundException("Not exists expert to create a offer to an order"));
        if (findExpert.getExpertStatus() != ExpertStatus.CONFIRMED) {
            throw new CustomizedIllegalArgumentException("Expert must be in confirmed status by admin");
        }
        Order findOrder = orderService.findOrderById(orderId)
                .orElseThrow(() -> new NotFoundException("Not exists order with "
                        + offer.getOrder().getId() + " id  to create a offer for that"));
        checkConstraint(findOrder, findExpert, offer);
        offer.setOrder(findOrder);
        offer.setExpert(findExpert);
        offerRepository.save(offer);
    }


    @Override
    public Optional<Offer> findOfferById(Long id) {
        return offerRepository.findById(id);
    }


    @Transactional
    @Override
    public List<Offer> findAllOfferOneOrderByPrice(Long id) {
        return offerRepository.findAllOfferOneOrderByPrice(id);
    }


    @Transactional
    @Override
    public List<Offer> findAllOfferOneOrderByExpertScore(Long id) {
        return offerRepository.findAllOfferOneOrderByExpertScore(id);
    }


    @Transactional
    @Override
    public Optional<Offer> findOfferByOrderIdAndExpertId(Long orderId, Long expertId) {
        return offerRepository.findOfferByOrderIdAndExpertId(orderId, expertId);
    }


    @Transactional
    @Override
    public boolean isExistsByOrderIdAndExpertId(Long orderId, Long expertId) {
        return findOfferByOrderIdAndExpertId(orderId, expertId).isPresent();
    }



    @Transactional
    @Override
    public void editOffer(Offer offer, LocalDateTime startDate, LocalDateTime endDate, double price) {
        Offer findOffer = offerRepository.findById(offer.getId())
                .orElseThrow(() -> new NotFoundException("No exists Offer with id = " + offer.getId()));
        findOffer.setStartDate(startDate);
        findOffer.setEndDate(endDate);
        findOffer.setPrice(price);
        offerRepository.save(offer);
    }

    @Override
    public Integer countOfferByExpertId(Long expertId) {
        return offerRepository.countOfferByExpertId(expertId);
    }


    private void checkConstraint(Order order, Expert expert, Offer offer) {

        if (!(order.getSubService().getBasePrice() <= offer.getPrice())) {
            throw new PermissionDeniedException("Sub service base price is " + order.getSubService().getBasePrice()
                    + " Offer purposed price must be more than "
                    + order.getProposedPrice() + "  base sub service price");
        }
        if (!expert.getSubServiceSet().contains(order.getSubService())) {
            throw new PermissionDeniedException("the expert with  " + expert.getUsername()
                    + " username has not been added to the " + order.getSubService().getName() + " subservice !!");
        }
        if (expert.getExpertStatus() != ExpertStatus.CONFIRMED) {
            throw new CustomizedIllegalArgumentException(" expert must be confirmed by admin to add offer");
        }

        if (isExistsByOrderIdAndExpertId(order.getId(), expert.getId())) {
            throw new CustomizedIllegalArgumentException("this expert is sended offert to this order!!!!!");
        }
    }





}
