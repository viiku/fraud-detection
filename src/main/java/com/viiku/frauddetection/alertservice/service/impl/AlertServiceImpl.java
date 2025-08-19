package com.viiku.frauddetection.alertservice.service.impl;

import com.viiku.frauddetection.alertservice.model.dto.FraudAlertDto;
import com.viiku.frauddetection.detectionservice.models.dtos.TransactionDto;
import com.viiku.frauddetection.alertservice.model.entity.FraudAlertEntity;
import com.viiku.frauddetection.alertservice.model.mapper.FraudAlertMapper;
import com.viiku.frauddetection.alertservice.model.dto.response.FraudAlertResponse;
import com.viiku.frauddetection.alertservice.repository.FraudAlertRepository;
import com.viiku.frauddetection.alertservice.service.AlertService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class AlertServiceImpl implements AlertService {

    private final KafkaTemplate<String, TransactionDto> kafkaTemplate;
    private final FraudAlertRepository fraudAlertRepository;
    private final FraudAlertMapper fraudAlertMapper;

    public AlertServiceImpl(KafkaTemplate<String, TransactionDto> kafkaTemplate, FraudAlertRepository fraudAlertRepository, FraudAlertMapper fraudAlertMapper) {
        this.kafkaTemplate = kafkaTemplate;
        this.fraudAlertRepository = fraudAlertRepository;
        this.fraudAlertMapper = fraudAlertMapper;
    }

    public FraudAlertDto createAlert(FraudAlertDto alertDto) {

        FraudAlertEntity entity = fraudAlertMapper.mapToEntity(alertDto);
        FraudAlertEntity savedAlert = fraudAlertRepository.save(entity);

        // Send alert to notification system
//        kafkaTemplate.send("fraud-alerts", savedAlert);

        log.info("Fraud alert created: {} for account: {}",
                savedAlert.getAlertType(), savedAlert.getAccountId());

        return alertDto;
    }

    @Override
    public List<FraudAlertResponse> getOpenAlerts() {
        return List.of();
    }

    @Override
    public List<FraudAlertResponse> getAlertsByAccount(String accountId) {
        return List.of();
    }

    @Override
    public FraudAlertResponse updateAlertStatus(Long alertId, String status) {
        return null;
    }
}
