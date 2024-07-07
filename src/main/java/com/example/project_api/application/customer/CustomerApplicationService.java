package com.example.project_api.application.customer;

import com.example.project_api.domain.customer.model.Customer;
import com.example.project_api.domain.customer.service.CustomerService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomerApplicationService {
    private final CustomerService customerService;

    public CustomerApplicationService(CustomerService customerService) {
        this.customerService = customerService;
    }

    public Optional<Customer> getCustomerById(Long id) {
        return customerService.getCustomerById(id);
    }

}
