package com.example.project_api.interfaces;

import com.example.project_api.domain.common.events.CustomerCreatedEvent;
import com.example.project_api.domain.customer.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

import java.time.Duration;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class SinkDataExampleController {
    private static AtomicInteger counter = new AtomicInteger();

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @Scheduled(fixedRate = 1000)
    public void publishCoinPrice() {

        CustomerCreatedEvent customerCreatedEvent =
                new CustomerCreatedEvent(this,
                    new Customer(
                            Long.valueOf(counter.incrementAndGet()),
                            UUID.randomUUID().toString(),
                            UUID.randomUUID().toString()
                    )
                );
        applicationEventPublisher.publishEvent(customerCreatedEvent);
    }

}
