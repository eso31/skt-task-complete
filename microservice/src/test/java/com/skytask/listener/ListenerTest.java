package com.skytask.listener;

import com.skytask.common.Product;
import com.skytask.service.ProductService;
import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
public class ListenerTest {

    private ProductService productService;

    private Listener listener;

    private Product product;

    @Before
    public void setUp() {
        productService = EasyMock.createMock(ProductService.class);
        listener = new Listener(productService);
        product = new Product();
        product.setName("testName");
        product.setDescription("testDescription");
        product.setStock(10);
        product.setPrice(25.5);
    }

    @Test
    public void testCreateProductHappyPath() {

        EasyMock.expect(productService.create(product)).andReturn(1L);
        EasyMock.replay(productService);

        listener.createProduct(product);

        EasyMock.verify(productService);
    }

    @Test
    public void testGetProductList() throws IOException {

        String expected = "[{\"id\":null,\"name\":\"testName\",\"description\":\"testDescription\",\"price\":25.5,\"stock\":10}]";

        EasyMock.expect(productService.getProducts()).andReturn(Collections.singletonList(product));
        EasyMock.replay(productService);

        String response = listener.getProductList("getProductList");
        assertEquals(expected, response);

        EasyMock.verify(productService);
    }
}