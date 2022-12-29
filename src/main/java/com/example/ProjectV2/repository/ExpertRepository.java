package com.example.ProjectV2.repository;

import com.example.ProjectV2.entity.Customer;
import com.example.ProjectV2.entity.Expert;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public interface ExpertRepository extends JpaRepository<Expert, Long>, JpaSpecificationExecutor<Expert> {

    Optional<Expert> findExpertByUsername(String username);

    void deleteExpertByUsername(String username);






}

