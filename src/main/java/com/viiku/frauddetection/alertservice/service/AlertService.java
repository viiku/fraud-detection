package com.viiku.frauddetection.alertservice.service;

import com.viiku.frauddetection.alertservice.model.dto.AlertDto;
import com.viiku.frauddetection.alertservice.model.dto.response.AlertResponse;


import java.util.List;

public interface AlertService {

    void createAlert(AlertDto alertDto);

    List<AlertResponse> getOpenAlerts();

    List<AlertResponse> getAlertsByAccount(String accountId);

    AlertResponse updateAlertStatus(Long alertId, String status);
}
