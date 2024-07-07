package com.example.project_api.domain.customer.service;

import com.example.project_api.domain.common.events.CustomerCreatedEvent;
import com.example.project_api.domain.customer.model.Customer;
import com.example.project_api.domain.customer.repository.CustomerRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

@Service
public class CustomerService {
    public final CustomerRepository customerRepository;
    private final ApplicationEventPublisher eventPublisher;

    public CustomerService(CustomerRepository customerRepository,
                           ApplicationEventPublisher eventPublisher) {
        this.customerRepository = customerRepository;
        this.eventPublisher = eventPublisher;

        // 매 초마다 새로운 고객을 추가하는 스케줄링 작업
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(this::addNewCustomerPeriodically, 0, 1, TimeUnit.SECONDS);
    }

    public Customer createCustomer(String name, String email) {
        Customer customer = new Customer();
        customer.setName(name);
        customer.setEmail(email);
        return customerRepository.save(customer);
    }

    // 매 초마다 새로운 고객을 생성하고 이벤트를 발생시키는 메서드
    private void addNewCustomerPeriodically() {
        Customer newCustomer = createCustomer("New Customer", "newcustomer@example.com");
        eventPublisher.publishEvent(new CustomerCreatedEvent(this, newCustomer));
    }


    public Optional<Customer> getCustomerById(Long id) {
        return customerRepository.findById(id);
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

}
