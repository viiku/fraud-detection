//package com.viiku.frauddetection.utils;
//
//import com.viiku.frauddetection.detectionservice.models.dtos.TransactionDto;
//import org.springframework.context.event.EventListener;
//import org.springframework.stereotype.Component;
//
//import java.math.BigDecimal;
//
//@Component
//public class TransactionValidation {
//
//    @EventListener
//    public void validateTransaction(TransactionDto transaction) {
//        if (transaction.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
//            throw new InvalidTransactionException("Invalid amount");
//        }
//
//        if (isInvalidIpAddress(transaction.getIpAddress())) {
//            throw new InvalidTransactionException("Invalid IP address");
//        }
//
//        // Additional validation rules
//    }
//
//}
