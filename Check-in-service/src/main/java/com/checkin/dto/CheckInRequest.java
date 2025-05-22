package com.checkin.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Request body for check-in process")
public class CheckInRequest {

    @Schema(description = "Booking ID", example = "101")
    private Long bookingId;

    @Schema(description = "Flight ID", example = "501")
    private Long flightId;

    @Schema(description = "User ID", example = "301")
    private Long userId;

    @Schema(description = "Seat number assigned to the passenger", example = "12A")
    private String seatNumber;

    public CheckInRequest() {
        // No-args constructor for Swagger/Jackson
    }

    public CheckInRequest(Long bookingId, Long flightId, Long userId, String seatNumber) {
        this.bookingId = bookingId;
        this.flightId = flightId;
        this.userId = userId;
        this.seatNumber = seatNumber;
    }

    public Long getBookingId() {
        return bookingId;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public Long getFlightId() {
        return flightId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setSeatNumber(String seatNumber) {
        this.seatNumber = seatNumber;
    }

    public void setBookingId(Long bookingId) {
        this.bookingId = bookingId;
    }

    public void setFlightId(Long flightId) {
        this.flightId = flightId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

}
