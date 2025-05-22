package com.checkin.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Schema(description = "Model representing an email message")
public class Email {

    @Schema(description = "Recipient email address", example = "user@example.com")
    private String to;

    @Schema(description = "Subject of the email", example = "Flight Booking Confirmation")
    private String subject;

    @Schema(description = "Body content of the email", example = "Your flight has been successfully booked.")
    private String body;

    //    public Email() {
//        // No-arg constructor
//    }
//
//    public Email(String to, String subject, String body) {
//        this.to = to;
//        this.subject = subject;
//        this.body = body;
//    }
    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }


}