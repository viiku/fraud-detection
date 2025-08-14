package com.viiku.frauddetection.service.Impl;

import com.viiku.frauddetection.models.dtos.TransactionDto;
import com.viiku.frauddetection.models.entities.TransactionEntity;
import com.viiku.frauddetection.models.enums.TransactionStatus;
import com.viiku.frauddetection.models.mappers.TransactionMapper;
import com.viiku.frauddetection.models.payloads.request.TransactionRequest;
import com.viiku.frauddetection.models.payloads.response.TransactionResponse;
import com.viiku.frauddetection.repository.TransactionRepository;
import com.viiku.frauddetection.service.TransactionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final RedisTemplate<String, Object> redisTemplate;
    private final TransactionMapper transactionMapper;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public TransactionServiceImpl(TransactionRepository transactionRepository, RedisTemplate redisTemplate, TransactionMapper transactionMapper, KafkaTemplate<String, Object> kafkaTemplate) {
        this.transactionRepository = transactionRepository;
        this.redisTemplate = redisTemplate;
        this.transactionMapper = transactionMapper;
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public TransactionResponse processTransaction(TransactionRequest request) {

        TransactionDto transactionDto = buildTransactionDto(request);
        TransactionEntity savedTransaction = transactionRepository.save(transactionMapper.mapToEntity(transactionDto));
//
//        // Cache recent transaction for quick access
//        cacheTransaction(transactionDto);
//
//        // Send to Kafka for fraud detection
        kafkaTemplate.send("transaction-events", savedTransaction);
//
//        // Update metrics
//        transactionCounter.increment();
//
//        log.info("Transaction processed: {}", savedTransaction.getTransactionId());
//        return savedTransaction;
        return buildTransactionResponse(transactionDto);
    }

    @Override
    @Cacheable(value = "recentTransactions", key = "#accountId")
    public List<TransactionResponse> getRecentTransactions(String accountId) {

        List<TransactionEntity> transactionEntities = transactionRepository.findRecentTransactionsByAccount(
                accountId, LocalDateTime.now().minusHours(24));

        List<TransactionResponse> responseList = new ArrayList<>();
        for (TransactionEntity entity: transactionEntities) {
            responseList.add(buildTransactionResponse(transactionMapper.mapToDto(entity)));
        }
        return responseList;
    }

    private void cacheTransaction(TransactionDto transaction) {
        String key = "recent:transactions:" + transaction.getAccountId();
        redisTemplate.opsForList().leftPush(key, transaction);
        redisTemplate.opsForList().trim(key, 0, 99); // Keep last 100 transactions
        redisTemplate.expire(key, Duration.ofHours(24));
    }

    private TransactionDto buildTransactionDto(TransactionRequest request) {
        return TransactionDto.builder()
                .transactionId(request.getTransactionId())
                .accountId(request.getAccountId())
                .bankId(request.getBankId())
                .amount(request.getAmount())
                .currency(request.getCurrency())
                .transactionType(request.getTransactionType())
                .merchantId(request.getMerchantId())
                .location(request.getLocation())
                .timestamp(request.getTimestamp())
                .ipAddress(request.getIpAddress())
                .deviceId(request.getDeviceId())
                .channel(request.getChannel())
                .build();
    }

    private TransactionResponse buildTransactionResponse(TransactionDto dto) {
        return TransactionResponse.builder()
                .transactionId(dto.getTransactionId())
                .accountId(dto.getAccountId())
                .bankId(dto.getBankId())
                .amount(dto.getAmount())
                .currency(dto.getCurrency())
                .transactionType(dto.getTransactionType())
                .merchantId(dto.getMerchantId())
                .location(dto.getLocation())
                .timestamp(dto.getTimestamp())
                .ipAddress(dto.getIpAddress())
                .deviceId(dto.getDeviceId())
                .channel(dto.getChannel())
                .status(TransactionStatus.APPROVED)
                .message("Transaction approved")
                .suspectedFraud(false)
                .build();
    }

    public Long getTransactionCountSince(String accountId, LocalDateTime since) {
        return transactionRepository.countByAccountIdAndTimestampAfter(accountId, since);
    }
}
