package com.example.ProjectV2.service;

import com.example.ProjectV2.entity.Customer;
import com.example.ProjectV2.entity.Expert;
import com.example.ProjectV2.entity.Order;
import com.example.ProjectV2.entity.enums.OrderStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;
import java.util.Map;
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

    void setSumScore(Long expertId, double score);

    boolean checkUsername(String username);

    void showScore(Expert expert);

    void setScoreAfterJobEnd(Long offerId);

    void setExpertImage(String expertUsername, MultipartFile file);

    List<Expert> showAllExperts();

    void deleteExpert(String username);

    List<Order> showAllOrderByExpertSubService(Long expertId);

    List<Order> showAllOrdersWaitingOffer(OrderStatus orderStatus);

    List<Expert> searchExpert(Map<String, String> predicateMap);


}
