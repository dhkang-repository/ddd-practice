package com.example.project_api.domain.common.events;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class ProductEventListener {
    @EventListener
    public void handleProductCreatedEvent(ProductCreatedEvent event) {
        // Handle the event, e.g., log it or trigger another service
        System.out.println("Product created: " + event.getName());
    }
}
