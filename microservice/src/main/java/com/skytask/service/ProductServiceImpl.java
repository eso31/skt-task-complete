package com.skytask.service;

import com.skytask.common.Product;
import com.skytask.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
class ProductServiceImpl implements ProductService {

    private ProductRepository productRepository;

    ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Long create(Product product) {
        return productRepository.insertProduct(product.getName(), product.getDescription(), product.getPrice(), product.getStock());
    }

    @Override
    public List<Product> getProducts() {
        return productRepository.getProducts();
    }

}
