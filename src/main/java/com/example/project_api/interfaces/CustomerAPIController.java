package com.example.project_api.interfaces;

import com.example.project_api.domain.common.events.CustomerCreatedEvent;
import com.example.project_api.domain.customer.model.Customer;
import com.example.project_api.domain.customer.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
public class CustomerAPIController {

    private final CustomerService customerService;
    private final Sinks.Many<CustomerCreatedEvent> sink;

    @Autowired
    public CustomerAPIController(CustomerService customerService) {
        this.customerService = customerService;
        this.sink = Sinks.many().multicast().onBackpressureBuffer();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Customer> getCustomer(@PathVariable("id") Long id) {
        return customerService.getCustomerById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<Customer>> getAllCustomers() {
        return ResponseEntity.ok(customerService.getAllCustomers());
    }


}
