package com.example.project_api.interfaces;

import com.example.project_api.domain.product.model.Product;
import com.example.project_api.domain.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/products")
public class ProductAPIController {
    @Autowired
    private ProductService productService;

    @GetMapping("/{id}")
    public Mono<Product> getProductById(@PathVariable("id") Long id) {
        return productService.getProductsById(id);
    }

    @GetMapping
    public Flux<Product> getAllProducts() {
        return productService.findAll();
    }

    @PostMapping
    public Mono<Product> createProduct(@RequestBody Product product) {
        return productService.createProduct(product);
    }
}
