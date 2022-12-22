package com.example.ProjectV2.repository;

import com.example.ProjectV2.entity.Expert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ExpertRepository extends JpaRepository<Expert, Long> {

    Optional<Expert> findExpertByUsername(String username);

    Expert findExpertById(Long id);


}

