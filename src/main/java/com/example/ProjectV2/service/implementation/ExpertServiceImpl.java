package com.example.ProjectV2.service.implementation;

import com.example.ProjectV2.entity.*;
import com.example.ProjectV2.entity.enums.ExpertStatus;
import com.example.ProjectV2.entity.enums.OrderStatus;
import com.example.ProjectV2.exception.*;
import com.example.ProjectV2.repository.ExpertRepository;
import com.example.ProjectV2.repository.SubServiceRepository;
import com.example.ProjectV2.service.*;
import com.example.ProjectV2.utils.QueryUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@org.springframework.stereotype.Service
public class ExpertServiceImpl implements ExpertService {

    private final ExpertRepository expertRepository;
    private final SubServiceRepository subServiceRepository;
    private final OfferService offerService;
    private final CustomerService customerService;
    private final OrderService orderService;


    @Autowired
    public ExpertServiceImpl(ExpertRepository expertRepository
            , SubServiceRepository subServiceRepository, OfferService offerService
            , CustomerService customerService, OrderService orderService) {
        this.expertRepository = expertRepository;
        this.subServiceRepository = subServiceRepository;
        this.offerService = offerService;
        this.customerService = customerService;
        this.orderService = orderService;
    }


    @Transactional
    @Override
    public Expert save(@Valid Expert expert) {
        if (checkUsername(expert.getUsername())) {
            return expertRepository.save(expert);
        }
        throw new NotUniqueException("expert username is exists");
    }


    @Transactional
    @Override
    public void changePassword(String username, String oldPassword, String newPassword) {
        Optional<Expert> expertOptional = findExpertByUsername(username);
        if (expertOptional.isEmpty()) {
            throw new NotFoundException("Not found expert to change password");
        }
        if (Objects.equals(expertOptional.get().getPassword(), oldPassword)) {
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


    @Transactional
    @Override
    public void addExpertToSubService(Expert expert, String subServiceName) {

        Optional<Expert> findExpertOptional = expertRepository.findExpertByUsername(expert.getUsername());
        if (findExpertOptional.isEmpty()) {
            throw new NotFoundException("Not found expert with this username");
        }
        Optional<SubService> findSubServiceOptional = subServiceRepository.findSubServiceByName(subServiceName);
        if (findSubServiceOptional.isEmpty()) {
            throw new NotFoundException("Not found sub service with this name");
        }
        Expert findExpert = findExpertOptional.get();
        SubService findSubService = findSubServiceOptional.get();
        if (findExpert.getExpertStatus() != ExpertStatus.CONFIRMED) {
            throw new CustomizedIllegalArgumentException("Expert must be in confirmed status by admin");
        }
        findSubService.addExpert(findExpert);
        subServiceRepository.save(findSubService);
    }


    @Transactional
    @Override
    public void deleteExpertFromSubService(Expert expert, String subServiceName) {

        Expert findExpert = expertRepository.findExpertByUsername(expert.getUsername()).orElseThrow(
                () -> new NotFoundException("Not found expert with this username"));

        SubService findSubServices = subServiceRepository.findSubServiceByName(subServiceName)
                .orElseThrow(() -> new NotFoundException("Not found subService with this sub service name"));

        findSubServices.getExpertSet().remove(findExpert);
        expertRepository.save(findExpert);
    }


    @Transactional
    @Override
    public void expertConfirm(Expert expert) {
        Optional<Expert> expertOptional = expertRepository.findExpertByUsername(expert.getUsername());
        if (expertOptional.isEmpty()) {
            throw new NotFoundException("Not found expert to confirm by admin");
        }
        Expert findExpert = expertOptional.get();
        findExpert.setExpertStatus(ExpertStatus.CONFIRMED);
        expertRepository.save(findExpert);
    }

    @Transactional
    @Override
    public void selectExpert(Long offerId, Long customerId) {
        Offer findOffer = offerService.findOfferById(offerId).orElseThrow(() -> new NotFoundException("not found offer"));
        Customer findCustomer = customerService.findById(customerId).orElseThrow(() -> new NotFoundException("not found customer"));
        Order order = findOffer.getOrder();
        if (!findCustomer.equals(order.getCustomer())) {
            throw new CustomizedIllegalArgumentException();
        }
        if (order.getExpert() != null) {
            throw new CustomizedIllegalArgumentException();
        }
        order.setOrderStatus(OrderStatus.COMING_EXPERTS);
        order.setAcceptedOffer(findOffer);
        order.setExpert(findOffer.getExpert());
        orderService.save(order);
    }

    @Transactional
    @Override
    public void setSumScore(Long expertId, double score) {
        Expert findExpert = expertRepository.findById(expertId).orElseThrow(() -> new NotFoundException("not exist expert"));
        findExpert.setScore(findExpert.getScore() + score);
        expertRepository.save(findExpert);
    }


    @Transactional
    @Override
    public boolean checkUsername(String username) {
        return expertRepository.findExpertByUsername(username).isEmpty();
    }


    @Transactional
    @Override
    public void showScore(Expert expert) {
        expertRepository.findExpertByUsername(expert.getUsername())
                .orElseThrow(() -> new NotFoundException(" Not found expert ! "));
        System.out.println("expert with username = " + expert.getUsername() + ", score is " + expert.getScore());
    }


    @Transactional
    @Override
    public void setScoreAfterJobEnd(Long offerId) {
        Offer findOffer = offerService.findOfferById(offerId).get();
        long hours = ChronoUnit.HOURS.between(findOffer.getEndDate(), LocalDateTime.now());
        Expert findExpert = findOffer.getExpert();
        double score = findExpert.getScore() - hours;
        findExpert.setScore(score);
        if (score < 0) {
            findExpert.setExpertStatus(ExpertStatus.DE_ACTIVE);
        }
        expertRepository.save(findExpert);
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
            } catch (IOException exception) {
                throw new ImageException("input image error");
            }
        } else {
            throw new ImageException("size or format image error");
        }
    }


    @Transactional
    @Override
    public void setExpertImage(String expertUsername, MultipartFile file) {
        byte[] bFile = null;
        if (file.getSize() / 1024 <= 300 && file.getOriginalFilename().endsWith(".jpg")) {
            try {
                bFile = file.getBytes();
            } catch (IOException e) {
                throw new ImageException("not found image");
            }
        } else {
            throw new ImageException("not match format or size ");
        }
        Expert findExpert = expertRepository.findExpertByUsername(expertUsername)
                .orElseThrow(() -> new NotFoundException("Not found expert with " + expertUsername + " username"));
        findExpert.setImage(bFile);
        expertRepository.save(findExpert);
    }


    @Override
    public List<Expert> showAllExperts() {
        return expertRepository.findAll();
    }

    @Transactional
    @Override
    public void deleteExpert(String username) {
        expertRepository.deleteExpertByUsername(username);
    }

    @Override
    public List<Order> showAllOrderByExpertSubService(Long expertId) {
        return orderService.showAllOrderByExpertSubService(expertId);
    }

    @Override
    public List<Order> showAllOrdersWaitingOffer(OrderStatus orderStatus) {
        return orderService.showAllOrdersWaitingOffer(orderStatus);
    }


    @Override
    public List<Expert> searchExpert(Map<String, String> predicateMap) {
        return expertRepository.findAll(returnSpecification(predicateMap));
    }

    Specification<Expert> returnSpecification(Map<String, String> predicateMap) {
        Specification<Expert> specification = Specification.where(null);
        for (Map.Entry<String, String> entry : predicateMap.entrySet()) {
            specification=specification.and((expert, cq, cb) -> cb.equal(expert.get(entry.getKey()), entry.getValue()));
        }
        return specification;
    }


}
