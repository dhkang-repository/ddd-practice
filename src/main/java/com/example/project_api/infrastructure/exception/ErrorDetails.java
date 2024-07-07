package com.example.project_api.infrastructure.exception;

import java.util.Date;

// 임시
public class ErrorDetails {
    private Date timestamp;
    private int status;
    private String message;
    private String details;

    public ErrorDetails(int status, String message, String details) {
        this.timestamp = new Date();
        this.status = status;
        this.message = message;
        this.details = details;
    }
}
