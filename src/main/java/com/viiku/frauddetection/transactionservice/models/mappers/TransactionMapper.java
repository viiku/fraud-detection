package com.viiku.frauddetection.transactionservice.models.mappers;

import com.viiku.frauddetection.common.model.mapper.BaseMapper;
import com.viiku.frauddetection.transactionservice.models.dtos.TransactionDto;
import com.viiku.frauddetection.transactionservice.models.entities.TransactionEntity;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Qualifier("packageMapper")
public class TransactionMapper implements BaseMapper<TransactionDto, TransactionEntity> {

    @Override
    public TransactionEntity mapToEntity(TransactionDto request) {
        if (request == null) {
            return null;
        }

        return TransactionEntity.builder()
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

    @Override
    public TransactionDto mapToDto(TransactionEntity entity) {
        return TransactionDto.builder()
                .transactionId(entity.getTransactionId())
                .accountId(entity.getAccountId())
                .bankId(entity.getBankId())
                .amount(entity.getAmount())
                .currency(entity.getCurrency())
                .transactionType(entity.getTransactionType())
                .merchantId(entity.getMerchantId())
                .location(entity.getLocation())
                .timestamp(entity.getTimestamp())
                .ipAddress(entity.getIpAddress())
                .deviceId(entity.getDeviceId())
                .channel(entity.getChannel())
                .build();
    }
}
