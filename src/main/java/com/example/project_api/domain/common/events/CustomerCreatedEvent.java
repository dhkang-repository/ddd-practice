package com.example.project_api.domain.common.events;

import com.example.project_api.domain.customer.model.Customer;
import org.springframework.context.ApplicationEvent;


public class CustomerCreatedEvent extends ApplicationEvent {
    private Long id;
    private String email;

    public CustomerCreatedEvent(Object source, Customer customer) {
        super(source);
        this.id = customer.getId();
        this.email = customer.getEmail();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
