package com.bookingservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Stripe payment session response")
public class StripeResponse {

    @Schema(description = "Status of the payment session", example = "success")
    private String status;

    @Schema(description = "Message regarding the payment process", example = "Payment session created successfully")
    private String message;

    @Schema(description = "Stripe session ID", example = "cs_test_a1B2c3D4e5F6g7H8")
    private String sessionId;

    @Schema(description = "URL to the Stripe payment page", example = "https://checkout.stripe.com/pay/cs_test_a1B2c3D4e5F6g7H8")
    private String sessionUrl;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getSessionUrl() {
        return sessionUrl;
    }

    public void setSessionUrl(String sessionUrl) {
        this.sessionUrl = sessionUrl;
    }

}
