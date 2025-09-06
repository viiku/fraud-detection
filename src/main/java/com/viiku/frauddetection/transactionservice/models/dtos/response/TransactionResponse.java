package com.viiku.frauddetection.transactionservice.models.dtos.response;

import com.viiku.frauddetection.transactionservice.models.enums.TransactionChannel;
import com.viiku.frauddetection.transactionservice.models.enums.TransactionStatus;
import com.viiku.frauddetection.transactionservice.models.enums.TransactionType;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionResponse {

    private String transactionId;
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
    private TransactionStatus status;
    private String message;
    private boolean suspectedFraud;
}
