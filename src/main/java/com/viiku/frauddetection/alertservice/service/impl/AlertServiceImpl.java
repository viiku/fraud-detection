package com.viiku.frauddetection.alertservice.service.impl;

import com.viiku.frauddetection.alertservice.model.dto.AlertDto;
import com.viiku.frauddetection.alertservice.model.dto.response.AlertResponse;
import com.viiku.frauddetection.alertservice.model.entity.AlertEntity;
import com.viiku.frauddetection.alertservice.model.mapper.AlertMapper;
import com.viiku.frauddetection.alertservice.repository.AlertRepository;
import com.viiku.frauddetection.alertservice.service.AlertService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class AlertServiceImpl implements AlertService {

    private final KafkaTemplate<String, AlertDto> kafkaTemplate;
    private final AlertRepository alertRepository;
    private final AlertMapper alertMapper;

    public AlertServiceImpl(KafkaTemplate<String, AlertDto> kafkaTemplate, AlertRepository alertRepository, AlertMapper alertMapper) {
        this.kafkaTemplate = kafkaTemplate;
        this.alertRepository = alertRepository;
        this.alertMapper = alertMapper;
    }

    /**
     * Create alert
     * @param alertDto alertDto
     */
    public void createAlert(AlertDto alertDto) {

        AlertEntity entity = alertMapper.mapToEntity(alertDto);

        log.info("Saving Alert to main database for accountId: {}", alertDto.getAccountId());
        AlertEntity savedAlert = alertRepository.save(entity);

        // Send alert to notification system
        kafkaTemplate.send("fraud-alerts", alertDto);
        log.info("Fraud alert created: {} for account: {}",
                savedAlert.getAlertType(), savedAlert.getAccountId());
    }

    /**
     * List all open alerts
     * @return list
     */
    @Override
    public List<AlertResponse> getOpenAlerts() {

        List<AlertEntity> alertEntityList = alertRepository.findAll();
        return List.of();
    }

    /**
     * List of alerts
     * @param accountId account
     * @return list
     */
    @Override
    public List<AlertResponse> getAlertsByAccount(String accountId) {
        List<AlertEntity> alertEntityList = alertRepository.findByAccountId(accountId);
        return List.of();
    }

    /**
     * Update Alert
     * @param alertId inputs alertId
     * @param status set alertStatus
     * @return alertResponse
     */
    @Override
    public AlertResponse updateAlertStatus(Long alertId, String status) {
        return null;
    }
}
