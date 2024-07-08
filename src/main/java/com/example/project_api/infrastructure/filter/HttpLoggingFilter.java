package com.example.project_api.infrastructure.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.UUID;

@WebFilter(urlPatterns = "/api/*")
@RequiredArgsConstructor
public class HttpLoggingFilter extends OncePerRequestFilter {
    private static final Logger logger = LogManager.getLogger(HttpLoggingFilter.class);
    private final ObjectMapper objectMapper;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest,
                                    HttpServletResponse httpServletResponse,
                                    FilterChain filterChain) throws ServletException, IOException {
        final UUID uuid = UUID.randomUUID();
        MDC.put("request_id", uuid.toString());

        ContentCachingRequestWrapper wrappedRequest = new ContentCachingRequestWrapper(httpServletRequest);
        ContentCachingResponseWrapper wrappedResponse = new ContentCachingResponseWrapper(httpServletResponse);

        // log the request
        logger.info("Incoming request : {}", objectMapper.writeValueAsString(new HashMap<>() {{
            put("request_id", MDC.get("request_id"));
            put("method", wrappedRequest.getMethod());
            put("uri", wrappedRequest.getRequestURI());
            put("params", wrappedRequest.getQueryString());
            put("body", getRequestBody(wrappedRequest));
        }}));

        filterChain.doFilter(wrappedRequest, wrappedResponse);

        logger.info("Outgoing response : {}", objectMapper.writeValueAsString(new HashMap<>() {{
            put("request_id", MDC.get("request_id"));
            put("status", wrappedResponse.getStatus());
            put("body", getResponseBody(wrappedResponse));
        }}));

        MDC.clear();
        wrappedResponse.copyBodyToResponse();
    }

    private String getRequestBody(ContentCachingRequestWrapper request) {
        byte[] buf = request.getContentAsByteArray();
        return buf.length > 0 ? new String(buf, 0, buf.length, StandardCharsets.UTF_8) : "";
    }

    private String getResponseBody(ContentCachingResponseWrapper response) {
        byte[] buf = response.getContentAsByteArray();
        return buf.length > 0 ? new String(buf, 0, buf.length, StandardCharsets.UTF_8) : "";
    }
}
