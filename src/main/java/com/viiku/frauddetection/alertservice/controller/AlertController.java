//package com.viiku.frauddetection.controllers;
//
//import com.viiku.frauddetection.models.dtos.response.payloads.FraudAlertResponse;
//import com.viiku.frauddetection.alertservice.service.AlertService;
//import io.swagger.v3.oas.annotations.Operation;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/api/v1/alerts")
//public class AlertController {
//
//    private final AlertService alertService;
//
//    public AlertController(AlertService alertService) {
//        this.alertService = alertService;
//    }
//
//    @GetMapping("/open")
//    @Operation(summary = "Get all open fraud alerts")
//    public ResponseEntity<List<FraudAlertResponse>> getOpenAlerts() {
//        List<FraudAlertResponse> alerts = alertService.getOpenAlerts();
//        return ResponseEntity.ok(alerts);
//    }
//
//    @GetMapping("/account/{accountId}")
//    @Operation(summary = "Get alerts for a specific account")
//    public ResponseEntity<List<FraudAlertResponse>> getAlertsByAccount(@PathVariable String accountId) {
//        List<FraudAlertResponse> alerts = alertService.getAlertsByAccount(accountId);
//        return ResponseEntity.ok(alerts);
//    }
//
//    @PutMapping("/{alertId}/status")
//    @Operation(summary = "Update alert status")
//    public ResponseEntity<FraudAlertResponse> updateAlertStatus(
//            @PathVariable Long alertId,
//            @RequestParam String status) {
//        FraudAlertResponse updatedAlert = alertService.updateAlertStatus(alertId, status);
//        return ResponseEntity.ok(updatedAlert);
//    }
//}
