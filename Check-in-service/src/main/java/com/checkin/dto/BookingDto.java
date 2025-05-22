package com.checkin.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
public class BookingDto {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Schema(description = "Auto-generated booking ID", example = "b123e456-e89b-12d3-a456-426614174000")
    private String bookingId;

    @Schema(description = "User ID of the person booking", example = "u123456")
    private String userId;

    @Schema(description = "Flight ID", example = "FL456")
    private String flightId;

    @Schema(description = "Passenger name", example = "John Doe", required = true)
    private String name;

    @Schema(description = "Passenger age", example = "30", required = true)
    private int age;

    @Schema(description = "Passenger email", example = "john.doe@example.com", required = true)
    private String email;

    @Schema(description = "Passenger address", example = "123 Main Street, NY")
    private String address;

    @Schema(description = "Departure city", example = "New York", required = true)
    private String fromCity;

    @Schema(description = "Arrival city", example = "London", required = true)
    private String toCity;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Schema(description = "Departure date (yyyy-MM-dd)", example = "2025-05-15")
    private Date departure;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Schema(description = "Arrival date (yyyy-MM-dd)", example = "2025-05-16")
    private Date arrival;

    @Schema(description = "Payment ID from Stripe", example = "pi_1ABCxyz")
    private String paymentId;

    @Schema(description = "Total payment amount", example = "5000")
    private Long paymentAmount;

    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFlightId() {
        return flightId;
    }

    public void setFlightId(String flightId) {
        this.flightId = flightId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getFromCity() {
        return fromCity;
    }

    public void setFromCity(String fromCity) {
        this.fromCity = fromCity;
    }

    public String getToCity() {
        return toCity;
    }

    public void setToCity(String toCity) {
        this.toCity = toCity;
    }

    public Date getDeparture() {
        return departure;
    }

    public void setDeparture(Date departure) {
        this.departure = departure;
    }

    public Date getArrival() {
        return arrival;
    }

    public void setArrival(Date arrival) {
        this.arrival = arrival;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public Long getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(Long paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

}

