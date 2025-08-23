package com.viiku.frauddetection.transactionservice.service;

import com.viiku.frauddetection.alertservice.model.dto.AlertDto;
import com.viiku.frauddetection.transactionservice.models.dtos.TransactionDto;

import java.util.List;

public interface AnomalyDetectionService {

    List<AlertDto> detectAnomalies(TransactionDto transactionDto);
}
