package com.example.ProjectV2.repository;

import com.example.ProjectV2.entity.Credit;
import com.example.ProjectV2.entity.Customer;
import com.example.ProjectV2.entity.Order;
import com.example.ProjectV2.entity.enums.OrderStatus;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long>, JpaSpecificationExecutor<Customer> {

    Optional<Customer> findCustomerByUsername(String username);

    void deleteCustomerByUsername(String username);

    Optional<Customer> findCustomerByVerificationCode(String code);

    Optional<Customer> findCustomerById(Long customerId);






}
