package com.example.ProjectV2.repository;

import com.example.ProjectV2.entity.Order;
import com.example.ProjectV2.entity.enums.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {



    @Query("""
select o from Order as o join SubService as ss on o.subService=ss join Expert as e where e.id=:expertId
""")
    List<Order> showAllOrderByExpertSubService(Long expertId);


    List<Order> findAllByOrderStatus(OrderStatus orderStatus);




}
