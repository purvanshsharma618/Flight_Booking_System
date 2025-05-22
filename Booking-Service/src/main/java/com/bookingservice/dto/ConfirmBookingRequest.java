package com.bookingservice.dto;

import lombok.Data;

@Data
public class ConfirmBookingRequest {
    private String sessionId;

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
}

