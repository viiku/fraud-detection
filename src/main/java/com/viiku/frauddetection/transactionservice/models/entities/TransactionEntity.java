package com.viiku.frauddetection.transactionservice.models.entities;

import com.viiku.frauddetection.common.model.entity.BaseEntity;
import com.viiku.frauddetection.transactionservice.models.enums.TransactionChannel;
import com.viiku.frauddetection.transactionservice.models.enums.TransactionType;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Setter
@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "transactions")

public class TransactionEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String transactionId;

    @Column(nullable = false)
    private String accountId;

    @Column(nullable = false)
    private String bankId;

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(nullable = false)
    private String currency;

    @Column(nullable = false)
    private TransactionType transactionType;

    @Column(nullable = false)
    private String merchantId;

    @Column(nullable = false)
    private String location;

    @Column(nullable = false)
    private LocalDateTime timestamp;

    @Column
    private String ipAddress;

    @Column
    private String deviceId;

    @Column
    private TransactionChannel channel;
}
