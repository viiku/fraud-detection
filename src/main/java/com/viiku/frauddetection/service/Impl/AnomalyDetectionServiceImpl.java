package com.viiku.frauddetection.service.Impl;

import com.viiku.frauddetection.models.dtos.FraudAlertDto;
import com.viiku.frauddetection.models.dtos.TransactionDto;
import com.viiku.frauddetection.service.AnomalyDetectionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Slf4j
@Service
public class AnomalyDetectionServiceImpl implements AnomalyDetectionService {

    @Override
    public Collection<FraudAlertDto> detectAnomalies(TransactionDto transactionDto) {
        return List.of();
    }
}
