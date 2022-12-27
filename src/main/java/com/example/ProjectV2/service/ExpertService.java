package com.example.ProjectV2.service;

import com.example.ProjectV2.entity.Expert;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.Optional;

@Service
public interface ExpertService {

    Expert save(Expert expert);

    void saveExpertWithPicture(Expert expert, File file);

    void changePassword(String username, String oldPassword, String newPassword);

    void changePassword(Expert expert, String newPassword);

    Optional<Expert> findExpertByUsername(String username);

    boolean loginExpert(String username, String password);

    Optional<Expert> findExpertById(Long id);

    void addExpertToSubService(Expert expert, String subServiceName);

    void deleteExpertFromSubService(Expert expert, String subServiceName);

    void expertConfirm(Expert expert);

    void selectExpert(Long offerId, Long customerId);
    void setSumScore(Long expertId);

    boolean checkUsername(String username);

    void showScore(Expert expert);

    void setScoreAfterJobEnd(Long offerId);
}
