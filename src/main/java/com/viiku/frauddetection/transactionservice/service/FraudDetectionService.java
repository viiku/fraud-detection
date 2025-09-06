package com.viiku.frauddetection.transactionservice.service;

import com.viiku.frauddetection.transactionservice.models.dtos.TransactionDto;

public interface FraudDetectionService {

    void detectFraud(TransactionDto transactionDto);
}
