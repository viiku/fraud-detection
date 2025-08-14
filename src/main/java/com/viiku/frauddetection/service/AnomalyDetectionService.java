package com.viiku.frauddetection.service;

import com.viiku.frauddetection.models.dtos.FraudAlertDto;
import com.viiku.frauddetection.models.dtos.TransactionDto;

import java.util.Collection;

public interface AnomalyDetectionService {

    Collection<FraudAlertDto> detectAnomalies(TransactionDto transactionDto);
}
