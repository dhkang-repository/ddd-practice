package com.example.project_api.infrastructure.persistence.customer;

import com.example.project_api.domain.product.model.Product;
import com.example.project_api.domain.product.repository.ProductRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@Repository
public class JsonProductRepository implements ProductRepository {

    private List<Product> products;

    @PostConstruct
    public void init() throws Exception {
        ClassPathResource resource = new ClassPathResource("domain/products.json");
        Path path = Paths.get(resource.getURI());
        ObjectMapper mapper = new ObjectMapper();
        TypeReference<List<Product>> typeReference = new TypeReference<List<Product>>() {};
        InputStream inputStream = new BufferedInputStream(new FileInputStream(path.toFile()));
        try {
            products = mapper.readValue(inputStream, typeReference);
        } catch (IOException e) {
            throw new RuntimeException("Failed to read products.json", e);
        }
    }

    @Override
    public Mono<Product> findById(Long id) {
        Optional<Product> product = products.stream().filter(p -> p.getId().equals(id)).findFirst();
        return product.map(Mono::just).orElseGet(Mono::empty);
    }

    @Override
    public Flux<Product> findAll() {
        return Flux.fromIterable(products);
    }

    @Override
    public Mono<Product> save(Product product) {
        products.add(product);
        return Mono.just(product);
    }
}
