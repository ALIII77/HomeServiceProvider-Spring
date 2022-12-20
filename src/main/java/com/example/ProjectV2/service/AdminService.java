package com.example.ProjectV2.service;

import com.example.ProjectV2.entity.*;
import java.io.File;
import java.util.List;
import java.util.Optional;

@org.springframework.stereotype.Service
public interface AdminService  {

    Admin save(Admin admin);

    void changeAdminPassword(Admin admin , String newPassword);

    Optional<Admin> findAdminByUsername(String username);

}
