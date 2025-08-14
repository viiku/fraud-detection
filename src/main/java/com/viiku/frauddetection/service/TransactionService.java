package com.viiku.frauddetection.service;

import com.viiku.frauddetection.models.payloads.request.TransactionRequest;
import com.viiku.frauddetection.models.payloads.response.TransactionResponse;

import java.time.LocalDateTime;
import java.util.List;

public interface TransactionService {

    TransactionResponse processTransaction(TransactionRequest request);

    List<TransactionResponse> getRecentTransactions(String accountId);

    Long getTransactionCountSince(String accountId, LocalDateTime oneHourAgo);
}
