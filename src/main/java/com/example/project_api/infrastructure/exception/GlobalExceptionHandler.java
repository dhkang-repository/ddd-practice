package com.example.project_api.infrastructure.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.util.ContentCachingRequestWrapper;

import java.nio.charset.StandardCharsets;

@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger logger = LogManager.getLogger(GlobalExceptionHandler.class);

    // 특정 예외처리
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> handlerResourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(HttpStatus.NOT_FOUND.value(), ex.getMessage(), request.getDescription(false));
        logError(request, ex);
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<?> handleNoHandlerFoundException(NoHandlerFoundException ex, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(HttpStatus.NOT_FOUND.value(), "URI Not Found", request.getDescription(false));
        logError(request, ex);
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    // 일반적인 예외처리
    private void logError(WebRequest request, Exception ex) {
        String requestUri = ((ServletWebRequest) request).getRequest().getRequestURI();
        String requestBody = getRequestBody(((ServletWebRequest) request).getRequest());
        logger.error("Error occurred: uri={} body={} message={} stacktrace={}",
                requestUri,
                requestBody,
                ex.getMessage(),
                ex.getStackTrace()
        );
    }

    private String getRequestBody(HttpServletRequest request) {
        if (request instanceof ContentCachingRequestWrapper) {
            ContentCachingRequestWrapper wrapper = (ContentCachingRequestWrapper) request;
            byte[] buf = wrapper.getContentAsByteArray();
            return buf.length > 0 ? new String(buf, 0, buf.length, StandardCharsets.UTF_8) : "";
        }
        return "";
    }
}
