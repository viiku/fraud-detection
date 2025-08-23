package com.viiku.frauddetection.transactionservice.service;

import com.viiku.frauddetection.transactionservice.models.dtos.request.TransactionRequest;
import com.viiku.frauddetection.transactionservice.models.dtos.response.TransactionResponse;

import java.time.LocalDateTime;
import java.util.List;

public interface TransactionService {

    TransactionResponse processTransaction(TransactionRequest request);

    List<TransactionResponse> getRecentTransactions(String accountId, int pageNumber, int pageSize);

    Long getTransactionCountSince(String accountId, LocalDateTime oneHourAgo);
}
