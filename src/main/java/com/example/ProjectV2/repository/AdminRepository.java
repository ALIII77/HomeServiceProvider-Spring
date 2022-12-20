package com.example.ProjectV2.repository;


import com.example.ProjectV2.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {

    void deleteAdminByUsername(String username);

    Optional<Admin> findAdminByUsername(String username);


}
