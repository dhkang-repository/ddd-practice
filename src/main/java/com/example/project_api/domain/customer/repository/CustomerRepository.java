package com.example.project_api.domain.customer.repository;

import com.example.project_api.domain.customer.model.Customer;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository {
    Optional<Customer> findById(Long id);
    List<Customer> findAll();
    Customer save(Customer customer);
}
