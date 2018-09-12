package com.skytask.service;

import com.skytask.common.Product;
import com.skytask.common.ProductMapper;
import com.skytask.common.RabbitMQVariables;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class ProductServiceManagement {

    private RabbitTemplate rabbitTemplate;
    private RabbitMQVariables rabbitMQVariables;

    public ProductServiceManagement(RabbitTemplate rabbitTemplate, RabbitMQVariables rabbitMQVariables) {
        this.rabbitTemplate = rabbitTemplate;
        this.rabbitMQVariables = rabbitMQVariables;
    }

    public List<Product> getProductListRabbit() throws IOException {
        String productList = (String) rabbitTemplate.convertSendAndReceive(
                rabbitMQVariables.getExchange(),
                rabbitMQVariables.getRoutingKeys().get("productList"),
                "getProductList");
        return ProductMapper.json2List(productList, Product.class);
    }

    public Long createProductRabbit(Product product) throws IllegalArgumentException {
        if (product == null)
            throw new IllegalArgumentException();

        return (Long) rabbitTemplate.convertSendAndReceive(
                rabbitMQVariables.getExchange(),
                rabbitMQVariables.getRoutingKeys().get("createProduct"),
                product);
    }
}
