package com.viiku.frauddetection.transactionservice;

import com.viiku.frauddetection.transactionservice.models.dtos.TransactionDto;
import com.viiku.frauddetection.transactionservice.service.FraudDetectionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;

@Slf4j
@Configuration
public class TransactionConsumer {

    private final FraudDetectionService fraudDetectionService;

    public TransactionConsumer(FraudDetectionService fraudDetectionService) {
        this.fraudDetectionService = fraudDetectionService;
    }

    @KafkaListener(
            topics = "transaction-events",
            groupId = "fraud-detection-group",
            containerFactory = "transactionKafkaListenerContainerFactory"
    )
    public void processTransaction(
            @Payload TransactionDto transactionDto,
            @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
            @Header(KafkaHeaders.RECEIVED_PARTITION) int partition,
            @Header(KafkaHeaders.OFFSET) long offset,
            Acknowledgment acknowledgment) {

        log.info("Transaction received: {}", transactionDto);
        try {
            log.debug("Processing transaction: {} from topic: {}, partition: {}, offset: {}",
                    transactionDto.getTransactionId(), topic, partition, offset);

            fraudDetectionService.detectFraud(transactionDto);
            acknowledgment.acknowledge();
        }
//        catch (RetryableException e) {
//            // Send to retry topic
//            kafkaTemplate.send("transaction-events-retry", transaction);
//            ack.acknowledge();
//            throw new RuntimeException(e);
         catch (Exception e) {
            log.error("Error processing transaction: {}", transactionDto.getTransactionId(), e);
            // Could implement retry logic or DLQ here

        }
    }
}
