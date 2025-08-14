package com.viiku.frauddetection.controllers;

import com.viiku.frauddetection.models.Transaction;
import com.viiku.frauddetection.models.payloads.request.TransactionRequest;
import com.viiku.frauddetection.models.payloads.response.TransactionResponse;
import com.viiku.frauddetection.service.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping
    @Operation(summary = "Submit a new transaction for processing")
    public ResponseEntity<TransactionResponse> submitTransaction(@Valid @RequestBody TransactionRequest request) {
        TransactionResponse processedTransaction = transactionService.processTransaction(request);
        return ResponseEntity.ok(processedTransaction);
    }

    @GetMapping("/account/{accountId}/recent")
    @Operation(summary = "Get recent transactions for an account")
    public ResponseEntity<List<TransactionResponse>> getRecentTransactions(@PathVariable String accountId) {
        List<TransactionResponse> transactions = transactionService.getRecentTransactions(accountId);
        return ResponseEntity.ok(transactions);
    }
}
