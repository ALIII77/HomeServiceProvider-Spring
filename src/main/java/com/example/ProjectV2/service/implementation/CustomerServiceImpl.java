package com.example.ProjectV2.service.implementation;

import com.example.ProjectV2.entity.*;
import com.example.ProjectV2.exception.NotFoundException;
import com.example.ProjectV2.exception.NotUniqueException;
import com.example.ProjectV2.exception.PermissionDeniedException;
import com.example.ProjectV2.repository.CustomerRepository;
import com.example.ProjectV2.service.*;

import java.util.*;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;


@org.springframework.stereotype.Service
public class CustomerServiceImpl implements CustomerService {

    private final ApplicationContext applicationContext;
    private final CustomerRepository customerRepository;
    private final ServiceService serviceService;
    private final SubServiceService subServiceService;


    @Autowired
    public CustomerServiceImpl(ApplicationContext applicationContext, CustomerRepository customerRepository
            , ServiceService serviceService, SubServiceService subServiceService, TransactionService transactionService) {
        this.applicationContext = applicationContext;
        this.customerRepository = customerRepository;
        this.serviceService = serviceService;
        this.subServiceService = subServiceService;

    }


    @Transactional
    @Override
    public Customer save(@Valid Customer customer) {
        if (checkUsername(customer.getUsername())) {
            Credit credit = new Credit();
            customer.setCredit(credit);
            return customerRepository.save(customer);
        }
        throw new NotUniqueException("customer username is exists");
    }


    @Transactional
    @Override
    public void changePassword(Customer customer, String newPassword) {
        Customer findCustomer = customerRepository.findCustomerByUsername(customer.getUsername())
                .orElseThrow(() -> new NotFoundException("Not found customer to change password"));
        if (!customer.getPassword().equals(findCustomer.getPassword())) {
            throw new PermissionDeniedException("password incorrect");
        }
        System.out.println("Customer Find!");
        if ((findCustomer.getPassword().equals(newPassword))) {
            throw new PermissionDeniedException("Enter new password that not equal with old password");
        }
        findCustomer.setPassword(newPassword);
        customerRepository.save(findCustomer);

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
            specification=specification.and((expert, cq, cb) -> cb.equal(expert.get(entry.getKey()), entry.getValue()));
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


}