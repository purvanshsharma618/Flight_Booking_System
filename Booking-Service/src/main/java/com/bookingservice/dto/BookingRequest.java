package com.bookingservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

@Schema(description = "Payment request details for Stripe")
public class BookingRequest {

    @Schema(description = "Name of the product or user", example = "Flight Ticket")
    private String name;

    @Schema(description = "Amount to be paid", example = "5000")
    private Long amount;

    @Schema(description = "Quantity of the item being purchased", example = "1")
    private Long quantity;

    @Schema(description = "Currency code (e.g., INR, USD)", example = "INR")
    private String currency;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

}
