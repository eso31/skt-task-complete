package com.skytask.listener;

import com.skytask.common.Product;
import com.skytask.common.ProductMapper;
import com.skytask.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class Listener {

    private final static Logger LOGGER = LoggerFactory.getLogger(Listener.class);

    private ProductService productService;

    Listener(ProductService productService){
        this.productService = productService;
    }

    @RabbitHandler
    @RabbitListener(queues = "productListQ")
    public String getProductList(@Payload String request) throws IOException {
        LOGGER.info("I received {}", request);
        List<Product> products = null;
        try {
            products = productService.getProducts();
        } catch(Exception e){
            LOGGER.error("Error: {}", e.getMessage());
        }
        return ProductMapper.list2Json(products);
    }

    @RabbitHandler
    @RabbitListener(queues = "createProductQ")
    public Long createProduct(@Payload Product product) {
        LOGGER.info("I received {}", product);
        Long id = null;
        try {
            id = productService.create(product);
        } catch(Exception e){
            LOGGER.error("Error: {}", e.getMessage());
        }
        return id;
    }

}
