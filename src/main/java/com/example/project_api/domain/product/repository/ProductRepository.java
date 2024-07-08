package com.example.project_api.domain.product.repository;

import com.example.project_api.domain.product.model.Product;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Optional;

public interface ProductRepository {
    Mono<Product> findById(Long id);
    Mono<Product> save(Product product);
    Flux<Product> findAll();
}
