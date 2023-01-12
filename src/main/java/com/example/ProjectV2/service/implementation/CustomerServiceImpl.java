package com.example.ProjectV2.service.implementation;

import com.example.ProjectV2.entity.*;
import com.example.ProjectV2.entity.enums.OrderStatus;
import com.example.ProjectV2.exception.CustomizedIllegalArgumentException;
import com.example.ProjectV2.exception.NotFoundException;
import com.example.ProjectV2.exception.NotUniqueException;
import com.example.ProjectV2.exception.PermissionDeniedException;
import com.example.ProjectV2.repository.CustomerRepository;
import com.example.ProjectV2.service.*;

import java.time.LocalDateTime;
import java.util.*;

import com.example.ProjectV2.utils.Convertor;
import com.example.ProjectV2.utils.SendEmail;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.SetJoin;
import jakarta.persistence.criteria.Subquery;
import jakarta.validation.Valid;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.Query;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;


@org.springframework.stereotype.Service
public class CustomerServiceImpl implements CustomerService {

    private final ApplicationContext applicationContext;
    private final CustomerRepository customerRepository;
    private final ServiceService serviceService;
    private final SubServiceService subServiceService;
    private final PasswordEncoder passwordEncoder;
    private final JavaMailSender mailSender;
    private final SendEmail sendEmail;


    @Autowired
    public CustomerServiceImpl(ApplicationContext applicationContext, CustomerRepository customerRepository
            , ServiceService serviceService, SubServiceService subServiceService
            , PasswordEncoder passwordEncoder
            , JavaMailSender mailSender, SendEmail sendEmail) {
        this.applicationContext = applicationContext;
        this.customerRepository = customerRepository;
        this.serviceService = serviceService;
        this.subServiceService = subServiceService;
        this.passwordEncoder = passwordEncoder;
        this.mailSender = mailSender;
        this.sendEmail = sendEmail;
    }


    @Transactional
    @Override
    public Customer save(@Valid Customer customer) {
        if (checkUsername(customer.getUsername())) {
            Credit credit = new Credit();
            customer.setCredit(credit);
            String encodedPassword = passwordEncoder.encode(customer.getPassword());
            customer.setPassword(encodedPassword);

            String randomCode = RandomString.make(64);
            customer.setVerificationCode(randomCode);
            customer.setEnabled(false);
            return customerRepository.save(customer);
        }
        throw new NotUniqueException("customer username is exists");
    }


    @Transactional
    @Override
    public void changePassword(Customer customer, String newPassword) {
        Customer customer1 = customerRepository.findCustomerByUsername(customer.getUsername())
                .orElseThrow(() -> new NotFoundException("Not found customer to change password"));

        if (!passwordEncoder.matches(customer.getPassword(),customer1.getPassword())) {
            throw new CustomizedIllegalArgumentException(" incorrect password ");
        }
        System.out.println("customer Find!");

        if (passwordEncoder.matches(newPassword,customer1.getPassword())) {
            throw new CustomizedIllegalArgumentException("Enter new password that not equal with old password");
        }
        customer1.setPassword(passwordEncoder.encode(newPassword));
        customerRepository.save(customer1);
    }


    @Transactional
    @Override
    public boolean loginCustomer(String username, String password) {
        Optional<Customer> optionalCustomer = customerRepository.findCustomerByUsername(username);
        if (optionalCustomer.isPresent()) {
            if (Objects.equals(optionalCustomer.get().getPassword(), password)) {
                System.out.println("WellCome");
                return true;
            }
            throw new PermissionDeniedException(" incorrect password ");
        }
        throw new NotFoundException(" Not fount Customer");
    }


    @Override
    public Optional<Customer> findCustomerByUsername(String username) {
        return customerRepository.findCustomerByUsername(username);
    }

    @Override
    public Optional<Customer> findById(Long id) {
        return customerRepository.findById(id);
    }

    @Override
    public List<Service> showAllServices() {
        return serviceService.findAllServices();
    }

    @Override
    public List<SubService> showAllSubServiceByService(Service service) {
        return subServiceService.findAllSubServicesByService(service);
    }


    @Override
    public boolean checkUsername(String username) {
        return customerRepository.findCustomerByUsername(username).isEmpty();
    }

    @Transactional
    @Override
    public void cartToCartPaymentWages(Long orderId, double amount) {
        creditService().credit(orderId, amount);
    }

    public void onlinePayment(Long orderId, double amount) {
        creditService().online(orderId, amount);
    }

    @Override
    public List<Customer> showAllCustomer() {
        return customerRepository.findAll();
    }

    @Transactional
    @Override
    public void deleteCustomer(String username) {
        customerRepository.deleteCustomerByUsername(username);
    }


    @Override
    @Transactional
    public List<Customer> searchCustomer(Map<String, String> predicateMap) {
        return customerRepository.findAll(returnSpecification(predicateMap));
    }

    Specification<Customer> returnSpecification(Map<String, String> predicateMap) {
        Specification<Customer> specification = Specification.where(null);
        for (Map.Entry<String, String> entry : predicateMap.entrySet()) {
            specification = specification.and((expert, cq, cb) -> cb.equal(expert.get(entry.getKey()), entry.getValue()));
        }
        return specification;
    }


    @Override
    public void credit(Long orderId, double amount) {
        creditService().credit(orderId, amount);
    }


    @Override
    public void online(Long orderId, double amount) {
        creditService().online(orderId, amount);
    }


    private CreditService creditService() {
        return applicationContext.getBean(CreditService.class);
    }


    @Override
    public boolean verify(String code) {
        Customer customer = customerRepository.findCustomerByVerificationCode(code)
                .orElseThrow(()->new CustomizedIllegalArgumentException("code not match"));
        if ( customer.isEnabled()) {
            throw new CustomizedIllegalArgumentException(" customer is enable!");
        } else {
            customer.setVerificationCode(null);
            customer.setEnabled(true);
            customerRepository.save(customer);
            return true;
        }
    }

    @Override
    public List<Customer> findAllCustomerByDateOfRegistration(LocalDateTime localDateTime) {
        List<Customer>findAllCustomer= customerRepository.findAll();
        List<Customer>resultCustomers=new ArrayList<>();
        for (Customer c:findAllCustomer) {
            if (c.getDateOfRegistration().equals(localDateTime)){
                resultCustomers.add(c);
            }
        }
        if (resultCustomers.isEmpty()) {
            throw new NotFoundException("Not exists customer with registration date  = "+ localDateTime);
        } else return resultCustomers;
    }




    @Transactional
    @Override
    public List<Customer> customerReport (Map<String, String> predicateMap){
        List<Customer>customerList = customerRepository.findAll(customerSpecification(predicateMap));
        return customerList;
    }




    private Specification<Customer> customerSpecification (Map<String, String> predicateMap) {
        Specification<Customer> specification = Specification.where(null);
        for (Map.Entry<String, String> entry : predicateMap.entrySet()) {
            specification = specification.and((customerRoot, cq, cb) ->
                    switch (entry.getKey()) {

                        case "id" -> cb.equal(customerRoot.get(Customer_.id), Convertor.toLong(entry.getValue()));

                        case "from" ->
                                cb.greaterThanOrEqualTo(customerRoot.get(Customer_.dateOfRegistration), Convertor.toLocalDateTime(entry.getValue()));

                        case "to" ->
                                cb.lessThanOrEqualTo(customerRoot.get(Customer_.dateOfRegistration), Convertor.toLocalDateTime(entry.getValue()));

                        case "orders" -> {
                            cq.distinct(true);
                            Subquery<Long> subQuery = cq.subquery(Long.class);
                            Root<Order> fromOrder = subQuery.from(Order.class);
                            subQuery.select(cb.count(fromOrder.get(Order_.id)));
                            subQuery.where(cb.equal(customerRoot.get(Customer_.id),fromOrder.get(Order_.customer).get(Customer_.id)));
                            yield cb.greaterThanOrEqualTo(subQuery,Convertor.toLong(entry.getValue()));
                        }



//                        @Query("""
//    select distinct  c  from  Customer as c where (select count (o.id) from Order as o where o.customer.id=c.id)>5
//""")



                        default -> throw new CustomizedIllegalArgumentException("not match query!");
                    });
        }
        return specification;
    }





    @Override
    public List<Order> customerOrderProfile(Map<String, String> predicateMap) {
        OrderService orderService = applicationContext.getBean(OrderService.class);
        return orderService.customerOrderProfile(predicateMap);
    }

    @Override
    public Credit findCreditByCustomerId(Long customerId) {
        return creditService().findCreditByCustomerId(customerId);
    }


}