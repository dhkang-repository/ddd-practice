package com.example.project_api.infrastructure.persistence.customer;

import com.example.project_api.domain.customer.model.Customer;
import com.example.project_api.domain.customer.repository.CustomerRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Repository;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@Repository
public class JsonCustomerRepository implements CustomerRepository {
    private List<Customer> customers;

    @PostConstruct
    public void init() throws Exception {
        ClassPathResource resource = new ClassPathResource("domain/customers.json");
        Path path = Paths.get(resource.getURI());
        ObjectMapper mapper = new ObjectMapper();
        TypeReference<List<Customer>> typeReference = new TypeReference<List<Customer>>() {};
        InputStream inputStream = new BufferedInputStream(new FileInputStream(path.toFile()));
        try {
            customers = mapper.readValue(inputStream, typeReference);
        } catch (IOException e) {
            throw new RuntimeException("Failed to read customers.json", e);
        }
    }

    @Override
    public Optional<Customer> findById(Long id) {
        return customers.stream().filter(customer -> customer.getId().equals(id)).findFirst();
    }

    @Override
    public List<Customer> findAll() {
        return customers;
    }

    @Override
    public Customer save(Customer customer) {
        customers.add(customer);
        return customer;
    }
}
