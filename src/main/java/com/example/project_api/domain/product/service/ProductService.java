package com.example.project_api.domain.product.service;

import com.example.project_api.domain.product.model.Product;
import com.example.project_api.domain.product.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public Mono<Product> createProduct(Product product) {
        return productRepository.save(product);
    }

    public Mono<Product> getProductsById(Long id) {
        return productRepository.findById(id);
    }

    public Flux<Product> findAll() {
        return productRepository.findAll();
    }
}
