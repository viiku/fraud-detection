package com.viiku.frauddetection.alertservice.service;

import com.viiku.frauddetection.alertservice.model.dto.AlertDto;
import com.viiku.frauddetection.alertservice.model.dto.response.FraudAlertResponse;

import java.util.List;

public interface AlertService {

    AlertDto createAlert(AlertDto alertDto);

    List<FraudAlertResponse> getOpenAlerts();

    List<FraudAlertResponse> getAlertsByAccount(String accountId);

    FraudAlertResponse updateAlertStatus(Long alertId, String status);
}
