package com.viiku.frauddetection.detectionservice.models.dtos;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.viiku.frauddetection.detectionservice.models.enums.TransactionChannel;
import com.viiku.frauddetection.detectionservice.models.enums.TransactionType;
import lombok.*;

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

    @JsonCreator
    public TransactionDto(
        @JsonProperty("transactionId") String transactionId,
        @JsonProperty("amount") BigDecimal amount) {
            this.transactionId = transactionId;
            this.amount = amount;
        }
}
