package com.example.inventoryservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.cloud.config.client.ConfigClientProperties;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component("inventory")
public class CustomHealthIndicator implements HealthIndicator {

    @Autowired
    private ConfigClientProperties configClientProperties;

    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    public Health health() {
        try {
            // Check connection to Config Server
            String url = configClientProperties.getUri()[0] + "/health";
            restTemplate.getForEntity(url, String.class);
            return Health.up()
                    .withDetail("configServer", "connected")
                    .build();
        } catch (Exception e) {
            return Health.down()
                    .withDetail("configServer", "disconnected")
                    .withException(e)
                    .build();
        }
    }
}
