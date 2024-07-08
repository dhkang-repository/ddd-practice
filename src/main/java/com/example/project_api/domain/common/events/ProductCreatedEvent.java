package com.example.project_api.domain.common.events;

import org.springframework.context.ApplicationEvent;

public class ProductCreatedEvent extends ApplicationEvent {
    private Long productId;
    private String name;
    private double price;

    public ProductCreatedEvent(Object source, Long productId, String name, double price) {
        super(source);
        this.productId = productId;
        this.name = name;
        this.price = price;
    }

    public Long getProductId() {
        return productId;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }
}
