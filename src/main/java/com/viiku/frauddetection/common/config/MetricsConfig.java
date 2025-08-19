package com.viiku.frauddetection.common.config;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MetricsConfig {

    @Bean
    public Counter transactionCounter(MeterRegistry meterRegistry) {
        return Counter.builder("transactions.processed")
                .description("Number of transactions processed")
                .register(meterRegistry);
    }

    @Bean
    public Counter fraudAlertCounter(MeterRegistry meterRegistry) {
        return Counter.builder("fraud.alerts")
                .description("Number of fraud alerts generated")
                .register(meterRegistry);
    }

    @Bean
    public Timer fraudDetectionTimer(MeterRegistry meterRegistry) {
        return Timer.builder("fraud.detection.time")
                .description("Time taken for fraud detection")
                .register(meterRegistry);
    }
}
