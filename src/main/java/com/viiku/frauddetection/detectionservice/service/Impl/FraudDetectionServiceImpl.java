package com.viiku.frauddetection.detectionservice.service.Impl;

import com.viiku.frauddetection.alertservice.model.dto.FraudAlertDto;
import com.viiku.frauddetection.detectionservice.models.dtos.TransactionDto;
import com.viiku.frauddetection.alertservice.model.enums.AlertType;
import com.viiku.frauddetection.detectionservice.models.enums.RiskLevel;
import com.viiku.frauddetection.detectionservice.models.dtos.response.TransactionResponse;
import com.viiku.frauddetection.alertservice.service.AlertService;
import com.viiku.frauddetection.detectionservice.service.AnomalyDetectionService;
import com.viiku.frauddetection.detectionservice.service.FraudDetectionService;
import com.viiku.frauddetection.detectionservice.service.TransactionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class FraudDetectionServiceImpl implements FraudDetectionService {

    private final TransactionService transactionService;
    private final AnomalyDetectionService anomalyDetectionService;
    private final AlertService alertService;

    public FraudDetectionServiceImpl(TransactionService transactionService, AnomalyDetectionService anomalyDetectionService, AlertService alertService) {
        this.transactionService = transactionService;
        this.anomalyDetectionService = anomalyDetectionService;
        this.alertService = alertService;
    }

    @Override
    public void detectFraud(TransactionDto transactionDto) {
//        Timer.Sample sample = Timer.start();
//
        try {
            List<FraudAlertDto> alerts = new ArrayList<>();

            // Rule-based detection
            alerts.addAll(runRuleBasedDetection(transactionDto));

            // Anomaly detection
            alerts.addAll(anomalyDetectionService.detectAnomalies(transactionDto));

//            // Process alerts
//            for (FraudAlertDto alert : alerts) {
//                alertService.createAlert(alert);
//                fraudAlertCounter.increment();
//                log.warn("Fraud alert created: {} for transaction: {}",
//                        alert.getAlertType(), transactionDto.getTransactionId());
//            }

        } finally {
//            sample.stop(fraudDetectionTimer);
        }

    }

    private List<FraudAlertDto> runRuleBasedDetection(TransactionDto transactionDto) {

        List<FraudAlertDto> alerts = new ArrayList<>();

        log.debug("Checking High Amount Transaction Rule for transaction Id: {}", transactionDto.getTransactionId());
        alerts.addAll(checkHighAmountRule(transactionDto));

        log.debug("Checking Transaction Velocity Rule for transaction Id: {}", transactionDto.getTransactionId());
        alerts.addAll(checkVelocityRule(transactionDto));

        log.debug("Checking Time Based Rule for transaction Id: {}", transactionDto.getTransactionId());
        alerts.addAll(checkTimeBasedRule(transactionDto));

        log.debug("Checking Location Based Rule for transaction Id: {}", transactionDto.getTransactionId());
        alerts.addAll(checkLocationRule(transactionDto));

        return alerts;
    }

    /**
     * Check for high amount transaction compare to threshold i.e. 10000
     * @param transactionDto
     * @return
     */
    private List<FraudAlertDto> checkHighAmountRule(TransactionDto transactionDto) {

        List<FraudAlertDto> alerts = new ArrayList<>();
        BigDecimal threshold = new BigDecimal("10000");

        if (transactionDto.getAmount().compareTo(threshold) > 0) {
            FraudAlertDto alert = FraudAlertDto.builder()
                    .transactionId(transactionDto.getTransactionId())
                    .accountId(transactionDto.getAccountId())
                    .alertType(AlertType.HIGH_AMOUNT)
                    .riskLevel(RiskLevel.MEDIUM)
                    .description("Transaction amount exceeds threshold: " + threshold)
                    .riskScore(0.8)
                    .build();

            alerts.add(alert);
        }

        return alerts;
    }

    /**
     * This check for transaction velocity
     * @param transactionDto
     * @return
     */
    private List<FraudAlertDto> checkVelocityRule(TransactionDto transactionDto) {
        List<FraudAlertDto> alerts = new ArrayList<>();
        LocalDateTime oneHourAgo = LocalDateTime.now().minusHours(1);

        Long recentTransactionCount = transactionService.getTransactionCountSince(
                transactionDto.getAccountId(), oneHourAgo);

        if (recentTransactionCount > 10) {
            FraudAlertDto alert = FraudAlertDto.builder()
                    .transactionId(transactionDto.getTransactionId())
                    .accountId(transactionDto.getAccountId())
                    .alertType(AlertType.HIGH_VELOCITY)
                    .riskLevel(RiskLevel.MEDIUM)
                    .description("High transaction velocity: " + recentTransactionCount + " transactions in 1 hour")
                    .riskScore(0.6)
                    .build();

            alerts.add(alert);
        }

        return alerts;
    }

    private List<FraudAlertDto> checkTimeBasedRule(TransactionDto transactionDto) {
        List<FraudAlertDto> alerts = new ArrayList<>();
        LocalTime transactionTime = transactionDto.getTimestamp().toLocalTime();

        // Unusual hours (2 AM - 6 AM)
        if (transactionTime.isAfter(LocalTime.of(2, 0)) &&
                transactionTime.isBefore(LocalTime.of(6, 0))) {

            FraudAlertDto alert = FraudAlertDto.builder()
                    .transactionId(transactionDto.getTransactionId())
                    .accountId(transactionDto.getAccountId())
                    .alertType(AlertType.UNUSUAL_TIME)
                    .riskLevel(RiskLevel.LOW)
                    .description("Transaction during unusual hours: " + transactionTime)
                    .riskScore(0.3)
                    .build();

            alerts.add(alert);
        }

        return alerts;
    }

    private List<FraudAlertDto> checkLocationRule(TransactionDto transactionDto) {
        List<FraudAlertDto> alerts = new ArrayList<>();

        // Get recent transactions for location comparison
        List<TransactionResponse> recentTransactions = transactionService.getRecentTransactions(
                transactionDto.getAccountId());

        boolean locationMismatch = recentTransactions.stream()
                .filter(t -> !t.getLocation().equals(transactionDto.getLocation()))
                .count() == recentTransactions.size();

        if (locationMismatch && !recentTransactions.isEmpty()) {
            FraudAlertDto alert = FraudAlertDto.builder()
                    .transactionId(transactionDto.getTransactionId())
                    .accountId(transactionDto.getAccountId())
                    .alertType(AlertType.LOCATION_ANOMALY)
                    .riskLevel(RiskLevel.MEDIUM)
                    .description("Transaction from unusual location: " + transactionDto.getLocation())
                    .riskScore(0.5)
                    .build();

            alerts.add(alert);
        }
        return alerts;
    }

}
