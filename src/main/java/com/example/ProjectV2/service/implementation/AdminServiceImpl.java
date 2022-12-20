package com.example.ProjectV2.service.implementation;

import com.example.ProjectV2.entity.*;
import com.example.ProjectV2.enums.ExpertStatus;
import com.example.ProjectV2.exception.CustomizedIllegalArgumentException;
import com.example.ProjectV2.exception.NotFoundException;
import com.example.ProjectV2.repository.*;
import com.example.ProjectV2.service.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import java.io.File;
import java.util.List;
import java.util.Optional;


@org.springframework.stereotype.Service
public class AdminServiceImpl implements AdminService {

    private final AdminRepository adminRepository;





    @Autowired
    public AdminServiceImpl(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }


    @Transactional
    @Override
    public Admin save(@Valid Admin admin) {             //test ok
        try {
            return adminRepository.save(admin);
        } catch (CustomizedIllegalArgumentException exception) {
            System.out.println(exception.getMessage());
        }
        return null;
    }

    @Transactional
    @Override
    public void changeAdminPassword(Admin admin, String newPassword) {      /*test ok*/
        Admin findAdmin = adminRepository.findAdminByUsername(admin.getUsername())
                .orElseThrow(() -> new NotFoundException("Not found admin to change password"));

        if (!admin.getPassword().equals(findAdmin.getPassword())) {
            throw new CustomizedIllegalArgumentException(" incorrect password ");
        }
        System.out.println("admin Find!");
        if ((findAdmin.getPassword().equals(newPassword))) {
            throw new CustomizedIllegalArgumentException("Enter new password that not equal with old password");
        }
        findAdmin.setPassword(newPassword);
        adminRepository.save(findAdmin);
    }

    @Override
    public Optional<Admin> findAdminByUsername(String username) {
        try {
            return adminRepository.findAdminByUsername(username);
        } catch (NotFoundException exception) {
            System.out.println(exception.getMessage());
        }
        return Optional.empty();
    }


}