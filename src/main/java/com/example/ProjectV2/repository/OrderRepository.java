package com.example.ProjectV2.repository;

import com.example.ProjectV2.entity.Expert;
import com.example.ProjectV2.entity.Order;
import com.example.ProjectV2.entity.enums.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long>, JpaSpecificationExecutor<Order> {



    @Query("""
    select o from Order as o join SubService as ss on o.subService=ss join Expert as e where e.id=:expertId
""")
    List<Order> showAllOrderByExpertSubService(Long expertId);


    @Query("""
    select o from Order as o where o.orderStatus=:orderStatus
""")
    List<Order>findAllByOrderStatus(OrderStatus orderStatus );




    @Query("""
    select o
    from Order as o 
    join SubService as ss
    on o.subService.id=ss.id
""")
    List<Order>findAllOrderBySubServiceId(Long subServiceId);



    @Query("""
    select count(o.acceptedOffer.id)from Order as o where o.expert.id=:expertId
""")
    int countOfOrderDoneWithExpertByExpertId(Long expertId);



    @Query("""
    select o from Order as o where o.orderStatus=:orderStatus and o.expert.id=:expertId
""")
    List<Order>findAllByOrderStatusAndExpertId(OrderStatus orderStatus,Long expertId);


    @Query("""
    select o from Order as o  
    join SubService as ss on o.subService=ss 
    join Expert as e where e.id=:expertId and o.orderStatus=:orderStatus
""")
    List<Order>showAllOrderByExpertSubServiceAndOrderStatus(Long expertId, OrderStatus orderStatus);




}
