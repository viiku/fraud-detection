package com.viiku.frauddetection.alertservice.service;

import com.viiku.frauddetection.alertservice.model.dto.FraudAlertDto;
import com.viiku.frauddetection.alertservice.model.dto.response.FraudAlertResponse;

import java.util.List;

public interface AlertService {

    FraudAlertDto createAlert(FraudAlertDto fraudAlertDto);

    List<FraudAlertResponse> getOpenAlerts();

    List<FraudAlertResponse> getAlertsByAccount(String accountId);

    FraudAlertResponse updateAlertStatus(Long alertId, String status);
}
