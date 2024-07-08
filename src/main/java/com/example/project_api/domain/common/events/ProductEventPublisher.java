package com.example.project_api.domain.common.events;

import com.example.project_api.domain.product.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class ProductEventPublisher {
    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    public void publishProductCreatedEvent(Product product) {
        ProductCreatedEvent event = new ProductCreatedEvent(this, product.getId(), product.getName(), product.getPrice());
        applicationEventPublisher.publishEvent(event);
    }
}
