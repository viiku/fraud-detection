package com.viiku.frauddetection.alertservice.model.dto;

import com.viiku.frauddetection.alertservice.model.enums.AlertStatus;
import com.viiku.frauddetection.alertservice.model.enums.AlertType;
import com.viiku.frauddetection.detectionservice.models.enums.RiskLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class FraudAlertDto {

    private final Long id;
    private final String transactionId;
    private String accountId;
    private AlertType alertType;
    private RiskLevel riskLevel;
    private String description;
    private Double riskScore;
    private AlertStatus status;
}
