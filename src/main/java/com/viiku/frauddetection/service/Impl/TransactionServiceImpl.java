package com.viiku.frauddetection.service.Impl;

import com.viiku.frauddetection.models.payloads.request.TransactionRequest;
import com.viiku.frauddetection.models.payloads.response.TransactionResponse;
import com.viiku.frauddetection.service.TransactionService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Override
    public TransactionResponse processTransaction(TransactionRequest request) {
        return null;
    }

    @Override
    public List<TransactionResponse> getRecentTransactions(String accountId) {
        return List.of();
    }
}
