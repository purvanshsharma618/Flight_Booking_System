package com.flightservice.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Schema(description = "Represents a flight and its details")
public class Flight {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Schema(description = "Unique identifier for the flight", example = "f1a9b2cd-345d-42f3-97e5-a3d6c80e1234")
    private String flightId;

    @Schema(description = "Name of the flight", example = "IndiGo 6E-205")
    private String flightName;

    @Schema(description = "City of departure", example = "Delhi")
    private String fromCity;

    @Schema(description = "City of arrival", example = "Mumbai")
    private String toCity;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @Schema(description = "Departure date and time", example = "2025-04-10T09:30:00")
    private LocalDateTime departureDateTime;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @Schema(description = "Arrival date and time", example = "2025-04-10T11:45:00")
    private LocalDateTime arrivalDateTime;

    @Transient
    @Schema(description = "Fare details for the flight", implementation = FareRequest.class)
    private FareRequest fareRequest;

    @Schema(description = "Availability of seats", example = "true")
    private boolean availableSeat;

    @ElementCollection
    @CollectionTable(name = "flight_available_seats", joinColumns = @JoinColumn(name = "flight_id"))
    @Column(name = "seat_number")
    @Schema(description = "List of available seat numbers")
    private List<Integer> availableSeats;

    public List<Integer> getAvailableSeats() {
        return availableSeats;
    }

    public void setAvailableSeats(List<Integer> availableSeats) {
        this.availableSeats = availableSeats;
    }

    public String getFlightId() {
        return flightId;
    }

    public String getToCity() {
        return toCity;
    }

    public LocalDateTime getDepartureDateTime() {
        return departureDateTime;
    }

    public LocalDateTime getArrivalDateTime() {
        return arrivalDateTime;
    }

    public String getFlightName() {
        return flightName;
    }

    public String getFromCity() {
        return fromCity;
    }

    public FareRequest getFareRequest() {
        return fareRequest;
    }

    public boolean isAvailableSeat() {
        return availableSeat;
    }

    public void setFlightId(String flightId) {
        this.flightId = flightId;
    }

    public void setToCity(String toCity) {
        this.toCity = toCity;
    }

    public void setFromCity(String fromCity) {
        this.fromCity = fromCity;
    }

    public void setFlightName(String flightName) {
        this.flightName = flightName;
    }

    public void setDepartureDateTime(LocalDateTime departureDateTime) {
        this.departureDateTime = departureDateTime;
    }

    public void setArrivalDateTime(LocalDateTime arrivalDateTime) {
        this.arrivalDateTime = arrivalDateTime;
    }

    public void setFareRequest(FareRequest fareRequest) {
        this.fareRequest = fareRequest;
    }

    public void setAvailableSeat(boolean availableSeat) {
        this.availableSeat = availableSeat;
    }
}