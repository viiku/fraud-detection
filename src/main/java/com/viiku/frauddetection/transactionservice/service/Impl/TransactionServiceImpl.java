package com.viiku.frauddetection.transactionservice.service.Impl;

import com.viiku.frauddetection.transactionservice.models.dtos.TransactionDto;
import com.viiku.frauddetection.transactionservice.models.entities.TransactionEntity;
import com.viiku.frauddetection.transactionservice.models.enums.TransactionStatus;
import com.viiku.frauddetection.transactionservice.models.mappers.TransactionMapper;
import com.viiku.frauddetection.transactionservice.models.dtos.request.TransactionRequest;
import com.viiku.frauddetection.transactionservice.models.dtos.response.TransactionResponse;
import com.viiku.frauddetection.transactionservice.repository.TransactionRepository;
import com.viiku.frauddetection.transactionservice.service.TransactionService;
import io.micrometer.core.instrument.Counter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation class of transaction apis
 */
@Slf4j
@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final RedisTemplate<String, Object> redisTemplate;
    private final TransactionMapper transactionMapper;
    private final KafkaTemplate<String, TransactionDto> kafkaTemplate;
    private final Counter transactionCounter;

    public TransactionServiceImpl(TransactionRepository transactionRepository, RedisTemplate redisTemplate, TransactionMapper transactionMapper, KafkaTemplate<String, TransactionDto> kafkaTemplate, Counter transactionCounter) {
        this.transactionRepository = transactionRepository;
        this.redisTemplate = redisTemplate;
        this.transactionMapper = transactionMapper;
        this.kafkaTemplate = kafkaTemplate;
        this.transactionCounter = transactionCounter;
    }

    /**
     * This method process transaction data i.e send events for fraud detection system
     * Saves data in cache
     * @param request takes input of transactionRequest
     * @return a simple response
     */
    @Override
    public TransactionResponse processTransaction(TransactionRequest request) {

        log.info("Processing raw transactions data in fraud detection system");

        TransactionDto transactionDto = buildTransactionDto(request);

        log.info("Storing raw transactions data to db");
        TransactionEntity savedTransaction = transactionRepository.save(transactionMapper.mapToEntity(transactionDto));

        // Send transactions data to Kafka for downstream
        // detection service should subscribe this topic
        // and do anomaly check many other checks
        log.info("Sending transactions events to kafka");
        kafkaTemplate.send("transaction-events", transactionDto);

        // Update metrics
        transactionCounter.increment();

//        log.info("Transaction processed: {}", savedTransaction.getTransactionId());
//        return savedTransaction;
        return buildTransactionResponse(transactionDto);
    }

    /**
     *
     * @param accountId Takes input as accountId
     * @return list of transactions for given accountId
     */
    @Override
//    @Cacheable(value = "recentTransactions", key = "#accountId")
    public List<TransactionResponse> getRecentTransactions(String accountId, int pageNumber, int pageSize) {

        Pageable pageable = PageRequest.of(pageNumber, pageSize);
//        Page<TransactionEntity> transactionEntities = transactionRepository.findRecentTransactionsByAccount(accountId,
//                LocalDateTime.now().minusHours(24));
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
