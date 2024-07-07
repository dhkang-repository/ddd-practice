package com.example.project_api.infrastructure.filter;


import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@jakarta.servlet.annotation.WebFilter("/sse*")
@Component
public class WebFluxFilter implements WebFilter {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        // Filter logic here
        return chain.filter(exchange);
    }
}
