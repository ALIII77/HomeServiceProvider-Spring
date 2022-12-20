package com.example.ProjectV2.repository;

import com.example.ProjectV2.entity.Offer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OfferRepository extends JpaRepository<Offer, Long> {

    @Query("select o from Offer o where o.order.id=:id order by o.price asc ")
    List<Offer> findAllOfferOneOrderByPrice(Long id);

    @Query("select o from Offer o where o.order.id=:id order by o.expert.score desc ")
    List<Offer> findAllOfferOneOrderByExpertScore(Long id);

    Optional<Offer> findOfferByOrderIdAndExpertId(Long orderId, Long expertId);


}
