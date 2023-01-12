package com.example.ProjectV2.repository;

import com.example.ProjectV2.entity.Credit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CreditRepository extends JpaRepository<Credit,Long> {

    @Query("select c.credit from Customer as c where c.id=:customerId ")
    Credit findCreditByCustomerId(Long customerId);

    @Query("select e.credit from Expert as e where e.id=:expertId ")
    Credit findCreditByExpertId(Long expertId);




}
