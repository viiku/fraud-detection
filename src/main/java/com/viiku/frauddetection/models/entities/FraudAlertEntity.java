package com.viiku.frauddetection.models.entities;

import com.viiku.frauddetection.common.model.entity.BaseEntity;
import com.viiku.frauddetection.models.enums.AlertStatus;
import com.viiku.frauddetection.models.enums.RiskLevel;
import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(
        name = "fraud_alert",
        indexes = {
                @Index(name = "idx_account_id", columnList = "accountId"),
                @Index(name = "idx_transaction_id", columnList = "transactionId")
        }
)
public class FraudAlertEntity extends BaseEntity {

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
