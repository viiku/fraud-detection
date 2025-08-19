package com.viiku.frauddetection.detectionservice.service.Impl;

import com.viiku.frauddetection.alertservice.model.dto.FraudAlertDto;
import com.viiku.frauddetection.detectionservice.models.dtos.TransactionDto;
import com.viiku.frauddetection.detectionservice.service.AnomalyDetectionService;
import com.viiku.frauddetection.detectionservice.service.TransactionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class AnomalyDetectionServiceImpl implements AnomalyDetectionService {

    private final TransactionService transactionService;

    public AnomalyDetectionServiceImpl(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @Override
    public List<FraudAlertDto> detectAnomalies(TransactionDto transactionDto) {

        List<FraudAlertDto> fraudAlertDtoList = new ArrayList<>();

        // Statistical anomaly detection
        fraudAlertDtoList.addAll(detectStatisticalAnomalies(transactionDto));

        // Pattern-based anomaly detection
        fraudAlertDtoList.addAll(detectPatternAnomalies(transactionDto));

        return fraudAlertDtoList;
    }

    List<FraudAlertDto> detectStatisticalAnomalies(TransactionDto transactionDto) {
        List<FraudAlertDto> alerts = new ArrayList<>();

//        List<TransactionDto> historicalTransactions = transactionService.getRecentTransactions(
//                transactionDto.getAccountId());
//
//        if (historicalTransactions.isEmpty()) {
//            return alerts;
//        }

        // Calculate average transaction amount
//        double avgAmount = historicalTransactions.stream()
//                .mapToDouble(t -> t.getAmount().doubleValue())
//                .average()
//                .orElse(0.0);
//
//        // Calculate standard deviation
//        double variance = historicalTransactions.stream()
//                .mapToDouble(t -> Math.pow(t.getAmount().doubleValue() - avgAmount, 2))
//                .average()
//                .orElse(0.0);

//        double stdDev = Math.sqrt(variance);
//        double currentAmount = transactionDto.getAmount().doubleValue();
//
//        // Z-score calculation
//        double zScore = Math.abs(currentAmount - avgAmount) / stdDev;
//
//        if (zScore > 3.0) { // More than 3 standard deviations
//            FraudAlertDto alert = FraudAlertDto.builder()
//                    .transactionId(transactionDto.getTransactionId())
//                    .accountId(transactionDto.getAccountId())
//                    .alertType(AlertType.STATISTICAL_ANOMALY)
//                    .riskLevel(RiskLevel.HIGH)
//                    .description("Transaction amount is statistically anomalous (Z-score: " + zScore + ")")
//                    .riskScore(0.1)
//                    .build();
//
//            alerts.add(alert);
//        }

        return alerts;
    }

    List<FraudAlertDto> detectPatternAnomalies(TransactionDto transactionDto) {
        List<FraudAlertDto> alerts = new ArrayList<>();

        // Implement pattern-based detection here
        // This could include machine learning models, behavioral patterns, etc.

        return alerts;
    }

}
