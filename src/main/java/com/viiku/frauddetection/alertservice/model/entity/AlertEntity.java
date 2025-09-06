package com.viiku.frauddetection.alertservice.model.entity;

import com.viiku.frauddetection.common.model.entity.BaseEntity;
import com.viiku.frauddetection.alertservice.model.enums.AlertStatus;
import com.viiku.frauddetection.transactionservice.models.enums.RiskLevel;
import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(
        name = "fraud_alert_info",
        indexes = {
                @Index(name = "idx_account_id", columnList = "accountId"),
                @Index(name = "idx_transaction_id", columnList = "transactionId")
        }
)
public class AlertEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String transactionId;

    @Column(nullable = false)
    private String accountId;

    @Column(nullable = false)
    private String alertType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RiskLevel riskLevel;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private Double riskScore;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AlertStatus status;
}
