package com.example.project_api.interfaces;

import com.example.project_api.domain.common.events.CustomerCreatedEvent;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.event.EventListener;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

import java.time.Duration;
import java.util.Date;

@Log4j2
@RestController
public class sseController {
    private final Sinks.Many<CustomerCreatedEvent> sink = Sinks.many().multicast().onBackpressureBuffer();;
    @GetMapping(value = "/sse/stream/sting", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> sseStreamString() {
        return Flux.interval(Duration.ofSeconds(1))
                .map(sequence -> "Time: " + new Date().toString());
    }

    // 예제를 위해 이벤트를 주기적으로 생성하는 메소드 추가
    public void publishEvent(CustomerCreatedEvent event) {

    }

    @EventListener
    public void handleCoinPriceEvent(CustomerCreatedEvent event) {
        Long id = event.getId();
        this.sink.tryEmitNext(event);
        log.info("received customer id : {}", id.toString());
    }

    @GetMapping(value = "/sse/stream/object",  produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ServerSentEvent<CustomerCreatedEvent>> sseStreamObject() {
        return this.sink.asFlux().map(event ->
                ServerSentEvent.<CustomerCreatedEvent>builder()
                        .id(String.valueOf(System.currentTimeMillis()))
                        .event("customer-updated")
                        .data(event)
                        .build()
        ).doOnCancel(() -> System.out.println("Client disconnected"));
    }
}
