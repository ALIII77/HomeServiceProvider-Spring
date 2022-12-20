package com.example.ProjectV2.service.implementation;

import com.example.ProjectV2.entity.Expert;
import com.example.ProjectV2.enums.ExpertStatus;
import com.example.ProjectV2.exception.CustomizedIllegalArgumentException;
import com.example.ProjectV2.exception.NotFoundException;
import com.example.ProjectV2.exception.PermissionDeniedException;
import com.example.ProjectV2.repository.ExpertRepository;
import com.example.ProjectV2.repository.SubServiceRepository;
import com.example.ProjectV2.service.*;
import com.example.ProjectV2.utils.QueryUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileInputStream;
import java.util.Objects;
import java.util.Optional;

@org.springframework.stereotype.Service
public class ExpertServiceImpl implements ExpertService {

    private final ExpertRepository expertRepository;



    @Autowired
    public ExpertServiceImpl(ExpertRepository expertRepository) {
        this.expertRepository=expertRepository;
    }


    @Transactional
    @Override
    public Expert save(@Valid Expert expert) {
        try {
            return expertRepository.save(expert);
        } catch (CustomizedIllegalArgumentException exception) {
            System.out.println(exception.getMessage());
        }
        return null;
    }


    @Transactional
    @Override
    public void saveExpertWithPicture(Expert expert, File file) {
        if (file.getName().endsWith(".jpg") && (file.length() / 1024 <= 300)) {
            try {
                QueryUtil.checkEntity(expert);
                FileInputStream fis = new FileInputStream(file);
                expert.setImage(fis.readAllBytes());
                fis.close();
                expertRepository.save(expert);
            } catch (Exception exception) {
                System.out.println(exception.getMessage());
            }
        } else {
            System.out.println("image format not jpg or image size not less than 300KB");
        }
    }


    @Transactional
    @Override
    public void changePassword(String username, String oldPassword, String newPassword) {      //test ok
        Optional<Expert> expertOptional = findExpertByUsername(username);
        if (expertOptional.isEmpty()) {
            throw new NotFoundException("Not found expert to change password");
        }
        if (Objects.equals(expertOptional.get().getPassword(), oldPassword)) {
            System.out.println("Expert Find!");
            Expert expert = expertOptional.get();
            if (expert.getExpertStatus() == ExpertStatus.CONFIRMED) {
                if (!(oldPassword.equals(newPassword))) {
                    expert.setPassword(newPassword);
                    expertRepository.save(expert);
                }
                throw new PermissionDeniedException("Enter new password that not equal with old password");
            } else {
                throw new PermissionDeniedException("Expert must be confirmed with admin!");
            }
        } else {
            throw new PermissionDeniedException("incorrect password");
        }
    }


    @Override
    public void changePassword(Expert expert, String newPassword) {
        Optional<Expert> expertOptional = expertRepository.findExpertByUsername(expert.getUsername());
        if (expertOptional.isEmpty()) {
            throw new NotFoundException("Not found expert to change password");
        }
        if (!expert.getPassword().equals(expertOptional.get().getPassword())) {
            throw new PermissionDeniedException("password incorrect");
        }
        System.out.println("expert Find!");
        Expert findExpert = expertOptional.get();
        if ((findExpert.getPassword().equals(newPassword))) {
            throw new PermissionDeniedException("Enter new password that not equal with old password");
        }
        findExpert.setPassword(newPassword);
        expertRepository.save(findExpert);
    }


    @Override
    public Optional<Expert> findExpertByUsername(String username) {
        return expertRepository.findExpertByUsername(username);
    }


    @Transactional
    @Override
    public boolean loginExpert(String username, String password) {
        Optional<Expert> expertOptional = expertRepository.findExpertByUsername(username);
        if (expertOptional.isPresent()) {
            if (Objects.equals(expertOptional.get().getPassword(), password)) {
                System.out.println(" WellCome ");
                return true;
            }
            throw new PermissionDeniedException(" incorrect password ");
        }
        throw new NotFoundException(" Customer Not found ");
    }


    @Override
    public Optional<Expert> findExpertById(Long id) {
        return expertRepository.findById(id);
    }






}
