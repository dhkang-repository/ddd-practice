package com.example.project_api.infrastructure.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.util.ContentCachingRequestWrapper;

import java.nio.charset.StandardCharsets;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger logger = LogManager.getLogger(GlobalExceptionHandler.class);

    // 특정 예외처리
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> handlerResourceNotFoundException(ResourceNotFoundException ex, HttpServletRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(HttpStatus.NOT_FOUND.value(), ex.getMessage(), "");
        logError(request, ex);
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<?> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpServletRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(HttpStatus.NOT_FOUND.value(), "URI Not Found", "");
        logError(request, ex);
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleOtherException(Exception ex, HttpServletRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), "");
        logError(request, ex);
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // 일반적인 예외처리
    private void logError(HttpServletRequest request, Exception ex) {
        String requestUri = request.getRequestURI();
        String requestBody = getRequestBody(request);
        String stackTrace = getStackTrace(ex);
        logger.error("[Error Occurred]\n[URI]:{}\n[Body]\n{}\n[Message]\n{}\n[Stacktrace]\n{}",
                requestUri,
                requestBody,
                ex.getMessage(),
                stackTrace
        );
    }

    private String getStackTrace(Exception ex) {
        StringBuilder sb = new StringBuilder();

        if (ex != null) {
            StackTraceElement[] stackTraces = ex.getStackTrace();
            StackTraceElement rootElement = stackTraces[0];
            sb.append(rootElement).append("\n");

            for (int i = 1; i < stackTraces.length; i++) {
                StackTraceElement element = stackTraces[i];
                String className = element.getClassName();
                if (className.contains("com.example.project")) {
                    sb.append("\tAt ").append(element).append("\n");
                }
            }
        }

        return sb.toString();
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
