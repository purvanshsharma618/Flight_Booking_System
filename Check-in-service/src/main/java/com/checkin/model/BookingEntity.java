package com.checkin.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;



@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Represents a booking made by a user for a flight")
public class BookingEntity {

    @Schema(description = "Unique identifier for the booking", example = "BKG12345")
    private String bookingId;

    @Schema(description = "ID of the user who made the booking", example = "USR1001")
    private String userId;

    @Schema(description = "ID of the booked flight", example = "FLT987")
    private String flightId;

    @Schema(description = "Name of the passenger", example = "Udit Patel")
    private String name;

    @Schema(description = "Age of the passenger", example = "28")
    private int age;

    @Schema(description = "Email address of the passenger", example = "udit@example.com")
    private String email;

    @Schema(description = "Passenger's address", example = "123 MG Road, Bhopal")
    private String address;

    @Schema(description = "City of departure", example = "Bhopal")
    private String fromCity;

    @Schema(description = "Destination city", example = "Delhi")
    private String toCity;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Schema(description = "Date of departure (yyyy-MM-dd)", example = "2025-04-15")
    private Date departure;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Schema(description = "Date of arrival (yyyy-MM-dd)", example = "2025-04-15")
    private Date arrival;

    @Schema(description = "Payment transaction ID", example = "PAY987654321")
    private String paymentId;

    @Schema(description = "Amount paid for the booking", example = "5999")
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
        this.email = email;
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
