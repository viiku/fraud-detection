package com.viiku.frauddetection.alertservice.model.dto;

import lombok.*;

import java.math.BigDecimal;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RuleDto {

    private String ruleName;
    private BigDecimal maxAmount;
    private Integer maxTransactionsPerHour;
    private Integer maxTransactionsPerDay;
    private Boolean checkVelocity;
    private Boolean checkLocation;
    private Boolean checkTime;
    private Double threshold;
}
