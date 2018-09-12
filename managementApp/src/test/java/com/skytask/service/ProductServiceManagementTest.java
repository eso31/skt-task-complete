package com.skytask.service;

import com.skytask.common.Product;
import com.skytask.common.ProductMapper;
import com.skytask.common.RabbitMQVariables;
import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.Collections;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
public class ProductServiceManagementTest {

    private RabbitTemplate rabbitTemplate;
    private RabbitMQVariables rabbitMQVariables;
    private ProductServiceManagement productServiceManagement;
    private Map<String, String> routingKeys;

    @Before
    public void setUp() {
        rabbitTemplate = EasyMock.createMock(RabbitTemplate.class);
        rabbitMQVariables = EasyMock.createMock(RabbitMQVariables.class);
        productServiceManagement = new ProductServiceManagement(rabbitTemplate, rabbitMQVariables);
        routingKeys = new HashMap<>();
        routingKeys.put("productList", "productListKey");
        routingKeys.put("createProduct", "createProductKey");
    }

    @Test
    public void getProductListRabbitEmptyTest() throws IOException {
        EasyMock.expect(rabbitMQVariables.getExchange()).andReturn("productExchange");
        EasyMock.expect(rabbitMQVariables.getRoutingKeys()).andReturn(routingKeys);
        EasyMock.expect(rabbitTemplate.convertSendAndReceive("productExchange", "productListKey", "getProductList")).andReturn("[]");
        EasyMock.replay(rabbitMQVariables);
        EasyMock.replay(rabbitTemplate);

        List<Product> products = productServiceManagement.getProductListRabbit();
        assertEquals(new ArrayList<Product>(), products);

        EasyMock.verify(rabbitMQVariables);
        EasyMock.verify(rabbitTemplate);
    }

    @Test
    public void getProductListRabbitNotEmptyTest() throws IOException {
        Product product = new Product();
        product.setId(1L);
        product.setName("Coca");
        product.setPrice(20);
        product.setDescription("Refresco de 2 Litros");
        product.setStock(25);

        List<Product> expectedList = Collections.singletonList(product);

        EasyMock.expect(rabbitMQVariables.getExchange()).andReturn("productExchange");
        EasyMock.expect(rabbitMQVariables.getRoutingKeys()).andReturn(routingKeys);
        EasyMock.expect(rabbitTemplate.convertSendAndReceive("productExchange", "productListKey", "getProductList")).andReturn(ProductMapper.list2Json(expectedList));
        EasyMock.replay(rabbitMQVariables);
        EasyMock.replay(rabbitTemplate);

        List<Product> products = productServiceManagement.getProductListRabbit();
        assertEquals(expectedList.toString(), products.toString());

        EasyMock.verify(rabbitMQVariables);
        EasyMock.verify(rabbitTemplate);
    }

    @Test
    public void createProductRabbitTest() {
        Product product = new Product();
        product.setId(1L);
        product.setName("Coca");
        product.setPrice(20);
        product.setDescription("Refresco de 2 Litros");
        product.setStock(25);

        EasyMock.expect(rabbitMQVariables.getExchange()).andReturn("productExchange");
        EasyMock.expect(rabbitMQVariables.getRoutingKeys()).andReturn(routingKeys);
        EasyMock.expect(rabbitTemplate.convertSendAndReceive("productExchange", "createProductKey", product)).andReturn(1L);
        EasyMock.replay(rabbitMQVariables);
        EasyMock.replay(rabbitTemplate);

        Long id = productServiceManagement.createProductRabbit(product);
        assertEquals(new Long(1), id);

        EasyMock.verify(rabbitMQVariables);
        EasyMock.verify(rabbitTemplate);
    }

    @Test(expected = IllegalArgumentException.class)
    public void createProductRabbitErrorTest() {
        Product product = null;

        EasyMock.expect(rabbitMQVariables.getExchange()).andReturn("productExchange");
        EasyMock.expect(rabbitMQVariables.getRoutingKeys()).andReturn(routingKeys);
        EasyMock.expect(rabbitTemplate.convertSendAndReceive("productExchange", "createProductKey", product)).andReturn("productCreated");
        EasyMock.replay(rabbitMQVariables);
        EasyMock.replay(rabbitTemplate);

        productServiceManagement.createProductRabbit(product);

        EasyMock.verify(rabbitMQVariables);
        EasyMock.verify(rabbitTemplate);
    }

}