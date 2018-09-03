package com.skytask.common;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.AsyncRabbitTemplate;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.amqp.SimpleRabbitListenerContainerFactoryConfigurer;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@EnableRabbit
@Configuration
public class Config {

    @Autowired
    private RabbitMQVariables rabbitMQVariables;

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public Queue responseProductListQueue() {
        return new Queue(rabbitMQVariables.getQueue().getResponseProductList());
    }

    @Bean
    public Queue requestProductListQueue() {
        return new Queue(rabbitMQVariables.getQueue().getRequestProductList());
    }

    @Bean
    public Queue createProductQueue() {
        return new Queue(rabbitMQVariables.getQueue().getCreateProduct());
    }

    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange(rabbitMQVariables.getExchange());
    }

    @Bean
    public List<Binding> bindings() {
        return Arrays.asList(
                BindingBuilder.bind(requestProductListQueue()).to(directExchange()).with(rabbitMQVariables.getRoutingKey().getRequestProductList()),
                BindingBuilder.bind(responseProductListQueue()).to(directExchange()).with(rabbitMQVariables.getRoutingKey().getResponseProductList()),
                BindingBuilder.bind(createProductQueue()).to(directExchange()).with(rabbitMQVariables.getRoutingKey().getCreateProduct())
        );
    }
}
