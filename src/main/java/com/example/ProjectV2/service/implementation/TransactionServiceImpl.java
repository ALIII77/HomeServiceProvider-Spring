package com.example.ProjectV2.service.implementation;

import com.example.ProjectV2.entity.*;
import com.example.ProjectV2.repository.TransactionRepository;
import com.example.ProjectV2.service.TransactionService;
import lombok.RequiredArgsConstructor;


@org.springframework.stereotype.Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;


    @Override
    public void save(Transaction transaction) {
        transactionRepository.save(transaction);
    }

}
