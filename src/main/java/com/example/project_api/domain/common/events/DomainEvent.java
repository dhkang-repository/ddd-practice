package com.example.project_api.domain.common.events;

public abstract class DomainEvent {
    private final long timestamp;

    protected DomainEvent(long timestamp) {
        this.timestamp = timestamp;
    }

    public long getTimestamp() {
        return timestamp;
    }
}
