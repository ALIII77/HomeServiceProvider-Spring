package com.example.ProjectV2.service.implementation;


import com.example.ProjectV2.entity.*;
import com.example.ProjectV2.entity.enums.OrderStatus;
import com.example.ProjectV2.entity.enums.TransactionType;
import com.example.ProjectV2.exception.CustomizedIllegalArgumentException;
import com.example.ProjectV2.exception.NotEnoughAmountException;
import com.example.ProjectV2.exception.NotFoundException;
import com.example.ProjectV2.repository.CreditRepository;
import com.example.ProjectV2.service.CreditService;
import com.example.ProjectV2.service.OrderService;
import com.example.ProjectV2.service.TransactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.transaction.annotation.Transactional;

@org.springframework.stereotype.Service
@RequiredArgsConstructor
public class CreditServiceImpl implements CreditService {


    private final ApplicationContext applicationContext;
    private final CreditRepository creditRepository;
    private final TransactionService transactionService;



    @Override
    public void save(Credit credit) {
        creditRepository.save(credit);
    }


    @Transactional
    @Override
    public void withdraw(@Valid Credit credit, double amount) {
        if (credit.getAmount()>=amount){
            credit.setAmount(credit.getAmount()-amount);
            creditRepository.save(credit);
        }else throw new NotEnoughAmountException("Not enough amount for withdraw");
    }


    @Transactional
    @Override
    public void deposit(@Valid Credit credit, double amount) {
        credit.setAmount(credit.getAmount()+((amount*70)/100));
        creditRepository.save(credit);
    }

    @Transactional
    @Override
    public void credit(Long orderId, double amount) {
        Order findOrder = orderService().findOrderById(orderId).orElseThrow(() -> new NotFoundException("Not found Order ! "));
        if (findOrder.getOrderStatus()!= OrderStatus.DONE){
            throw new CustomizedIllegalArgumentException("Order must be DONE state !");
        }
        Expert findExpert = findOrder.getExpert();
        Customer finCustomer = findOrder.getCustomer();
        Credit sourceCredit = finCustomer.getCredit();
        Credit destinationCredit = findExpert.getCredit();
        withdraw(sourceCredit,amount);
        deposit(destinationCredit,amount);
        Transaction transaction = new Transaction(
                findOrder.getCustomer().getCredit(),findOrder.getExpert().getCredit()
                , amount,findOrder, TransactionType.CART_TO_CART
        );
        transactionService.save(transaction);
        findOrder.setTransaction(transaction);
        orderService().save(findOrder);
    }


    @Transactional
    @Override
    public void online(Long orderId, double amount){
        Order findOrder = orderService().findOrderById(orderId).orElseThrow(() -> new NotFoundException("Not found Order ! "));
        if (findOrder.getOrderStatus()!= OrderStatus.DONE){
            throw new CustomizedIllegalArgumentException("Order must be DONE state !");
        }
        Expert findExpert = findOrder.getExpert();
        Credit destinationCredit = findExpert.getCredit();
        deposit(destinationCredit,amount);
        Transaction transaction = new Transaction(
                findOrder.getCustomer().getCredit(),findOrder.getExpert().getCredit()
                , amount,findOrder, TransactionType.ONLINE
        );
        transactionService.save(transaction);
        findOrder.setTransaction(transaction);
        orderService().save(findOrder);
    }


    private OrderService orderService(){
        return applicationContext.getBean(OrderService.class);
    }

}
