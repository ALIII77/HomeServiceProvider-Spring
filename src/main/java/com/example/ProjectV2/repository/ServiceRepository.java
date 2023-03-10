package com.example.ProjectV2.repository;

import com.example.ProjectV2.entity.Service;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ServiceRepository extends JpaRepository<Service, Long> {

    Optional<Service> findServiceByName(String name);

    List<Service> findAll();



}
