package com.viiku.frauddetection.models.dtos;

import com.viiku.frauddetection.models.enums.TransactionChannel;
import com.viiku.frauddetection.models.enums.TransactionType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Setter
@Getter
@Builder
@AllArgsConstructor
public class TransactionDto {

    private final String transactionId;
    private String accountId;
    private String bankId;
    private BigDecimal amount;
    private String currency;
    private TransactionType transactionType;
    private String merchantId;
    private String location;
    private LocalDateTime timestamp;
    private String ipAddress;
    private String deviceId;
    private TransactionChannel channel;
}
