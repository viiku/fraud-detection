package com.viiku.frauddetection.service;

import com.viiku.frauddetection.models.dtos.TransactionDto;

public interface FraudDetectionService {

    void detectFraud(TransactionDto transactionDto);
}
