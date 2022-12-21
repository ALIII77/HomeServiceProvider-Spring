package com.example.ProjectV2.service;

import com.example.ProjectV2.entity.Offer;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@org.springframework.stereotype.Service
public interface OfferService {

    void save(Offer offer);

    /*
        void addOffer(String expertUsername, double purposedPrice, double duration, Long orderId);
    */
    void addOffer(Offer offer, Long expertId);

    Optional<Offer> findOfferById(Long id);

    List<Offer> findAllOfferOneOrderByPrice(Long id);

    List<Offer> findAllOfferOneOrderByExpertScore(Long id);

    Optional<Offer> findOfferByOrderIdAndExpertId(Long orderId, Long expertId);

    boolean isExistsByOrderIdAndExpertId(Long orderId, Long expertId);

    void editOffer(Offer offer, LocalDateTime startDate , LocalDateTime endDate, double price);

}
