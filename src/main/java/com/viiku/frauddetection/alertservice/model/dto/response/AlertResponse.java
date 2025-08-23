package com.viiku.frauddetection.alertservice.model.dto.response;

import com.viiku.frauddetection.alertservice.model.enums.AlertStatus;
import com.viiku.frauddetection.alertservice.model.enums.AlertType;
import com.viiku.frauddetection.transactionservice.models.enums.RiskLevel;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AlertResponse {

    private Long id;
    private String transactionId;
    private String accountId;
    private AlertType alertType;
    private RiskLevel riskLevel;
    private String description;
    private Double riskScore;
    private AlertStatus status;
}
