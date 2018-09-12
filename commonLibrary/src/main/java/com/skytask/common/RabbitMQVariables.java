package com.skytask.common;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@ConfigurationProperties("rabbitmq.config")
public class RabbitMQVariables {
    private Map<String, String> queues;
    private String exchange;
    private Map<String, String> routingKeys;


    public Map<String, String> getQueues() {
        return queues;
    }

    public void setQueues(Map<String, String> queues) {
        this.queues = queues;
    }

    public String getExchange() {
        return exchange;
    }

    public void setExchange(String exchange) {
        this.exchange = exchange;
    }

    public Map<String, String> getRoutingKeys() {
        return routingKeys;
    }

    public void setRoutingKeys(Map<String, String> routingKeys) {
        this.routingKeys = routingKeys;
    }
}
