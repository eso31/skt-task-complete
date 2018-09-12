package com.skytask.service;

import com.skytask.common.Product;
import com.skytask.repository.ProductRepository;
import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;

@RunWith(SpringRunner.class)
public class ProductServiceTest {


    private ProductRepository productRepository;

    private ProductService service;

    @Before
    public void setUp() {
        productRepository = EasyMock.createMock(ProductRepository.class);
        service = new ProductServiceImpl(productRepository);
    }

    @Test
    public void testCreateProduct() {
        Product product = new Product();
        product.setName("testName");
        product.setDescription("testDescription");
        product.setStock(10);
        product.setPrice(25.5);

        EasyMock.expect(productRepository.insertProduct(product.getName(), product.getDescription(), product.getPrice(),
                product.getStock())).andReturn(1L);
        EasyMock.replay(productRepository);

        service.create(product);
        EasyMock.verify(productRepository);
    }

    @Test
    public void testGetProducts() {
        EasyMock.expect(productRepository.getProducts()).andReturn(Collections.emptyList());
        EasyMock.replay(productRepository);
        service.getProducts();
        EasyMock.verify(productRepository);
    }

}