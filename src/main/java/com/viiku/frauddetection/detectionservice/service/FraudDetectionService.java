package com.viiku.frauddetection.detectionservice.service;

import com.viiku.frauddetection.detectionservice.models.dtos.TransactionDto;

public interface FraudDetectionService {

    void detectFraud(TransactionDto transactionDto);
}
