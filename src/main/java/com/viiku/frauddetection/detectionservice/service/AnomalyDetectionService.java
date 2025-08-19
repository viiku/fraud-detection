package com.viiku.frauddetection.detectionservice.service;

import com.viiku.frauddetection.alertservice.model.dto.FraudAlertDto;
import com.viiku.frauddetection.detectionservice.models.dtos.TransactionDto;

import java.util.List;

public interface AnomalyDetectionService {

    List<FraudAlertDto> detectAnomalies(TransactionDto transactionDto);
}
