package com.viiku.frauddetection.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class RedisHealthCheck implements CommandLineRunner {

    private final RedisTemplate<String, Object> redisTemplate;

    public RedisHealthCheck(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void run(String... args) throws Exception {
        try {
            // Test Redis connectivity
            redisTemplate.opsForValue().set("health-check", "OK");
            String result = (String) redisTemplate.opsForValue().get("health-check");

            if ("OK".equals(result)) {
                log.info("✅ Redis connection is working properly");
                redisTemplate.delete("health-check"); // Clean up
            } else {
                log.error("❌ Redis connection test failed - unexpected result: {}", result);
            }
        } catch (Exception e) {
            log.error("❌ Redis connection failed: {}", e.getMessage());
            log.error("Please check your Redis server and configuration");
        }
    }
}
