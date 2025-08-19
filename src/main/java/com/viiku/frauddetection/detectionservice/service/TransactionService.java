package com.viiku.frauddetection.detectionservice.service;

import com.viiku.frauddetection.detectionservice.models.dtos.request.TransactionRequest;
import com.viiku.frauddetection.detectionservice.models.dtos.response.TransactionResponse;

import java.time.LocalDateTime;
import java.util.List;

public interface TransactionService {

    TransactionResponse processTransaction(TransactionRequest request);

    List<TransactionResponse> getRecentTransactions(String accountId, int pageNumber, int pageSize);

    Long getTransactionCountSince(String accountId, LocalDateTime oneHourAgo);
}
