package com.viiku.frauddetection.common.config;

import com.viiku.frauddetection.alertservice.model.dto.AlertDto;
import com.viiku.frauddetection.transactionservice.models.dtos.TransactionDto;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.StringSerializer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Configuration
public class KafkaConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    private Map<String, Object> baseProducerConfigs() {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        props.put(ProducerConfig.ACKS_CONFIG, "all");
        props.put(ProducerConfig.RETRIES_CONFIG, 3);
        return props;
    }

    private Map<String, Object> baseConsumerConfigs(String groupId) {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        props.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
        props.put(JsonDeserializer.USE_TYPE_INFO_HEADERS, false);
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        return props;
    }

    /**
     * Generic Builders
     */
    private <T> ProducerFactory<String, T> producerFactory() {
        return new DefaultKafkaProducerFactory<>(baseProducerConfigs());
    }

    private <T> ConsumerFactory<String, T> consumerFactory(Class<T> targetType, String groupId) {
        return new DefaultKafkaConsumerFactory<>(
                baseConsumerConfigs(groupId),
                new StringDeserializer(),
                new JsonDeserializer<>(targetType));
    }

    private <T> ConcurrentKafkaListenerContainerFactory<String, T> concurrentKafkaListenerContainerFactory(
            ConsumerFactory<String, T> consumerFactory) {
        ConcurrentKafkaListenerContainerFactory<String, T> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory);
        return factory;
    }

    /**
     * Beans for TransactionDto
     */
    @Bean
    public KafkaTemplate<String, TransactionDto> transactionKafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, TransactionDto> transactionKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, TransactionDto> factory =
                concurrentKafkaListenerContainerFactory(consumerFactory(TransactionDto.class, "fraud-detection-tx-group"));

        factory.setCommonErrorHandler(new DefaultErrorHandler(
                (record, exception) -> log.error("Error consuming record {}: {}", record, exception.getMessage())
        ));

        return factory;
    }

    /**
     * Beans for AlertDto
     */
    @Bean
    public KafkaTemplate<String, AlertDto> alertKafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, AlertDto> alertKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, AlertDto> factory =
                concurrentKafkaListenerContainerFactory(consumerFactory(AlertDto.class, "fraud-detection-alert-group"));

        factory.setCommonErrorHandler(new DefaultErrorHandler(
                (record, exception) -> log.error("Error consuming record {}: {}", record, exception.getMessage())
        ));
//        return concurrentKafkaListenerContainerFactory(consumerFactory(AlertDto.class, "fraud-detection-alert-group"));
        return factory;
    }
}
