package com.flightservice.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor


@Schema(description = "Request object containing fare details for a flight")
public class FareRequest {

    @Schema(description = "Unique ID of the flight", example = "f1a9b2cd-345d-42f3-97e5-a3d6c80e1234")
    private String flightId;

    @Schema(description = "Fare for economy class", example = "4500.0")
    private double economyFare;

    @Schema(description = "Fare for business class", example = "9500.0")
    private double businessFare;
    public double getBusinessFare() {
        return businessFare;
    }

    public double getEconomyFare() {
        return economyFare;
    }

    public String getFlightId() {
        return flightId;
    }

    public void setBusinessFare(double businessFare) {
        this.businessFare = businessFare;
    }

    public void setEconomyFare(double economyFare) {
        this.economyFare = economyFare;
    }

    public void setFlightId(String flightId) {
        this.flightId = flightId;
    }
}