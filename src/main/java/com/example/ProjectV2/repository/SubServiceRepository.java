package com.example.ProjectV2.repository;

import com.example.ProjectV2.entity.SubService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubServiceRepository extends JpaRepository<SubService, Long> {

    Optional<SubService> findSubServiceByName(String name);

    List<SubService> findAllSubServiceByServiceName(String serviceName);

    SubService findSubServiceById(Long id);

    @Modifying
    @Query("delete from SubService as s where s.id=:id")
    void deleteSubServiceById(Long id);


}
