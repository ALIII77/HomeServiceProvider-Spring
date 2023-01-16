package com.example.ProjectV2.service.implementation;

import com.example.ProjectV2.entity.*;
import com.example.ProjectV2.entity.enums.ExpertStatus;
import com.example.ProjectV2.entity.enums.OrderStatus;
import com.example.ProjectV2.exception.*;
import com.example.ProjectV2.repository.ExpertRepository;
import com.example.ProjectV2.repository.SubServiceRepository;
import com.example.ProjectV2.service.*;
import com.example.ProjectV2.utils.Convertor;
import com.example.ProjectV2.utils.QueryUtil;
import com.example.ProjectV2.utils.SendEmail;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.SetJoin;
import jakarta.persistence.criteria.Subquery;
import jakarta.validation.Valid;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

@org.springframework.stereotype.Service
public class ExpertServiceImpl implements ExpertService {

    private final ApplicationContext applicationContext;
    private final ExpertRepository expertRepository;
    private final SubServiceRepository subServiceRepository;
    private final CustomerService customerService;
    private final PasswordEncoder passwordEncoder;
    private final JavaMailSender mailSender;
    private final SendEmail sendEmail;


    @Autowired
    public ExpertServiceImpl(ApplicationContext applicationContext, ExpertRepository expertRepository
            , SubServiceRepository subServiceRepository
            , CustomerService customerService, PasswordEncoder passwordEncoder
            , JavaMailSender mailSender, SendEmail sendEmail) {
        this.applicationContext = applicationContext;
        this.expertRepository = expertRepository;
        this.subServiceRepository = subServiceRepository;
        this.customerService = customerService;
        this.passwordEncoder = passwordEncoder;
        this.mailSender = mailSender;
        this.sendEmail = sendEmail;
    }


    @Transactional
    @Override
    public Expert save(@Valid Expert expert) {
        if (checkUsername(expert.getUsername())) {
            Credit credit = new Credit();
            expert.setCredit(credit);
            String encodedPassword = passwordEncoder.encode(expert.getPassword());
            expert.setPassword(encodedPassword);

            String randomCode = RandomString.make(64);
            expert.setVerificationCode(randomCode);
            expert.setEnabled(false);
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
        Expert expert = expertOptional.get();
        if (passwordEncoder.matches(oldPassword, expert.getPassword())) {
            if (expert.getExpertStatus() == ExpertStatus.CONFIRMED) {
                if (!passwordEncoder.matches(newPassword, expert.getPassword())) {
                    expert.setPassword(passwordEncoder.encode(newPassword));
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
        if (!findExpert.isEnabled()) {
            throw new CustomizedIllegalArgumentException("First, the expert's email must be confirmed");
        }
        if (findExpert.getExpertStatus() != ExpertStatus.AWAITING_CONFIRM) {
            throw new CustomizedIllegalArgumentException("Expert status must be in AWAITING_CONFIRM");
        }
        findExpert.setExpertStatus(ExpertStatus.CONFIRMED);
        expertRepository.save(findExpert);
    }

    @Transactional
    @Override
    public void selectExpert(Long offerId, Long customerId) {
        OrderService orderService = applicationContext.getBean(OrderService.class);
        OfferService offerService = applicationContext.getBean(OfferService.class);
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
        OfferService offerService = applicationContext.getBean(OfferService.class);
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
        OrderService orderService = applicationContext.getBean(OrderService.class);
        return orderService.showAllOrderByExpertSubService(expertId);
    }

    @Override
    public List<Order> showAllOrdersWaitingOffer(OrderStatus orderStatus) {
        OrderService orderService = applicationContext.getBean(OrderService.class);
        return orderService.showAllOrdersWaitingOffer(orderStatus);
    }

    @Override
    public List<Order> showAllOrderByExpertSubServiceAndOrderStatus(Long expertId, OrderStatus orderStatus) {
        OrderService orderService = applicationContext.getBean(OrderService.class);
        return orderService.showAllOrderByExpertSubServiceAndOrderStatus(expertId, orderStatus);
    }


    @Override
    public List<Expert> searchExpert(Map<String, String> predicateMap) {
        return expertRepository.findAll(returnSpecification(predicateMap));
    }

    Specification<Expert> returnSpecification(Map<String, String> predicateMap) {
        Specification<Expert> specification = Specification.where(null);
        for (Map.Entry<String, String> entry : predicateMap.entrySet()) {
            specification = specification.and((expert, cq, cb) -> cb.equal(expert.get(entry.getKey()), entry.getValue()));
        }
        return specification;
    }


    @Override
    public boolean verify(String code) {
        Expert expert = expertRepository.findExpertByVerificationCode(code)
                .orElseThrow(() -> new CustomizedIllegalArgumentException("code not found"));
        if (expert.isEnabled()) {
            throw new CustomizedIllegalArgumentException(" customer is enable!");
        } else {
            expert.setVerificationCode(null);
            expert.setEnabled(true);
            expert.setExpertStatus(ExpertStatus.AWAITING_CONFIRM);
            expertRepository.save(expert);
            return true;
        }
    }

    @Override
    public List<Expert> findAllExpertByDateRegistration(LocalDateTime localDateTime) {
        List<Expert> findAllExpert = expertRepository.findAll();
        List<Expert> resultExpert = new ArrayList<>();
        for (Expert e : findAllExpert) {
            if (e.getDateOfRegistration().equals(localDateTime)) {
                resultExpert.add(e);
            }
        }
        if (resultExpert.isEmpty()) {
            throw new NotFoundException("Not exists customer with registration date  = " + localDateTime);
        } else return resultExpert;
    }


    @Transactional
    @Override
    public List<Expert> expertReport(Map<String, String> predicateMap) {
        List<Expert> expertList = expertRepository.findAll(expertDoOrderSpecification(predicateMap));
        return expertList;
    }


    public Specification<Expert> expertDoOrderSpecification(Map<String, String> predicateMap) {
        Specification<Expert> specification = Specification.where(null);
        for (Map.Entry<String, String> entry : predicateMap.entrySet()) {
            specification = specification.and((expertRoot, cq, cb) ->
                    switch (entry.getKey()) {

                        case "expertId" -> cb.equal(expertRoot.get(Expert_.id), Convertor.toLong(entry.getValue()));

                        case "from" ->
                                cb.greaterThanOrEqualTo(expertRoot.get(Expert_.dateOfRegistration), Convertor.toLocalDateTime(entry.getValue()));

                        case "to" ->
                                cb.lessThanOrEqualTo(expertRoot.get(Expert_.dateOfRegistration), Convertor.toLocalDateTime(entry.getValue()));

                        case "orders" -> {
                            cq.distinct(true);
                            Subquery<Long> subQuery = cq.subquery(Long.class);
                            Root<Order> fromOrder = subQuery.from(Order.class);
                            subQuery.select(cb.count(fromOrder.get(Order_.id)));
                            subQuery.where(cb.equal(expertRoot.get(Expert_.id), fromOrder.get(Order_.expert).get(Expert_.id)));
                            yield cb.greaterThanOrEqualTo(subQuery, Convertor.toLong(entry.getValue()));
                        }

                        case "enabled" ->
                                cb.equal(expertRoot.get(Expert_.enabled), Convertor.toBoolean(entry.getValue()));

                        case "personType" ->
                                cb.equal(expertRoot.get(Expert_.PERSON_TYPE), Convertor.toPersonType(entry.getValue()));

                        case "firstName" -> cb.equal(expertRoot.get(Expert_.firstName), entry.getValue());

                        case "lastName" -> cb.equal(expertRoot.get(Expert_.lastName), entry.getValue());

                        case "username" -> cb.equal(expertRoot.get(Expert_.username), entry.getValue());

                        case "email" -> cb.equal(expertRoot.get(Expert_.email), entry.getValue());

                        case "dateOfRegistration" ->
                                cb.equal(expertRoot.get(Expert_.dateOfRegistration), Convertor.toLocalDateTime(entry.getValue()));
                        case "score"->
                            cb.equal(expertRoot.get(Expert_.score),Convertor.toDouble(entry.getValue()));


                        default -> throw new CustomizedIllegalArgumentException("not match query!");
                    });
        }
        return specification;

    }


    private CreditService creditService() {
        return applicationContext.getBean(CreditService.class);
    }


    @Override
    public List<Order> expertOrderProfile(Map<String, String> predicateMap) {
        OrderService orderService = applicationContext.getBean(OrderService.class);
        return orderService.expertOrderProfile(predicateMap);
    }

    @Override
    public double findCreditByExpertId(Long expertId) {
        return creditService().findCreditByExpertId(expertId);
    }


}
