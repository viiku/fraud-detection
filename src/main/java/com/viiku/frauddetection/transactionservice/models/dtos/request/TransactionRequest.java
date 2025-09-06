package com.viiku.frauddetection.transactionservice.models.dtos.request;

import com.viiku.frauddetection.transactionservice.models.enums.TransactionChannel;
import com.viiku.frauddetection.transactionservice.models.enums.TransactionType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class TransactionRequest {

    @NotBlank(message = "Transaction Id should not be empty")
    private final String transactionId;

    @NotBlank(message = "Account Id should not be empty")
    private String accountId;

    @NotBlank(message = "Bank Id should not be empty")
    private String bankId;

    @NotNull(message = "Amount cannot be null")
    @DecimalMin(value = "0.01", message = "Amount must be greater than zero")
    private BigDecimal amount;

    @NotBlank(message = "Currency should not be empty")
    @Pattern(regexp = "^[A-Z]{3}$", message = "Currency must be a valid ISO 4217 code")
    private String currency;

    @NotNull(message = "Transaction Type should not be empty")
    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;

    private String merchantId;
    private String location;

//    @PastOrPresent(message = "Transaction timestamp cannot be in the future")
    private LocalDateTime timestamp;

    @Pattern(regexp = "^([0-9]{1,3}\\.){3}[0-9]{1,3}$", message = "Invalid IP address")
    private String ipAddress;

    private String deviceId;

    @Enumerated(EnumType.STRING)
    private TransactionChannel channel;
}
