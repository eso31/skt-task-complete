package com.skytask.common;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;

@EnableRabbit
@Configuration
public class RabbitConfiguration {

    @Autowired
    private RabbitMQVariables rabbitMQVariables;

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public Queue productListQueue() {
        return new Queue(rabbitMQVariables.getQueues().get("productList"));
    }

    @Bean
    public Queue createProductQueue() {
        return new Queue(rabbitMQVariables.getQueues().get("createProduct"));
    }

    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange(rabbitMQVariables.getExchange());
    }

    @Bean
    public List<Binding> bindings() {
        return Arrays.asList(
                BindingBuilder.bind(productListQueue()).to(directExchange()).with(rabbitMQVariables.getRoutingKeys().get("productList")),
                BindingBuilder.bind(createProductQueue()).to(directExchange()).with(rabbitMQVariables.getRoutingKeys().get("createProduct"))
        );
    }
}
