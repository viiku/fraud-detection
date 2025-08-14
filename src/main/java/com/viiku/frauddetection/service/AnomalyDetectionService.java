package com.viiku.frauddetection.service;

import com.viiku.frauddetection.models.dtos.FraudAlertDto;
import com.viiku.frauddetection.models.dtos.TransactionDto;

import java.util.Collection;
import java.util.List;

public interface AnomalyDetectionService {

    List<FraudAlertDto> detectAnomalies(TransactionDto transactionDto);
}
