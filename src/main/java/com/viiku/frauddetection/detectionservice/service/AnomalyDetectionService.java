package com.viiku.frauddetection.detectionservice.service;

import com.viiku.frauddetection.alertservice.model.dto.AlertDto;
import com.viiku.frauddetection.detectionservice.models.dtos.TransactionDto;

import java.util.List;

public interface AnomalyDetectionService {

    List<AlertDto> detectAnomalies(TransactionDto transactionDto);
}
