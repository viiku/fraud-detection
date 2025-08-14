package com.viiku.frauddetection.service;

import com.viiku.frauddetection.models.dtos.FraudAlertDto;
import com.viiku.frauddetection.models.payloads.response.FraudAlertResponse;

import java.util.List;

public interface AlertService {

    FraudAlertDto createAlert(FraudAlertDto fraudAlertDto);

    List<FraudAlertResponse> getOpenAlerts();

    List<FraudAlertResponse> getAlertsByAccount(String accountId);

    FraudAlertResponse updateAlertStatus(Long alertId, String status);
}
