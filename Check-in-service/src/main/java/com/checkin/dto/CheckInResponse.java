package com.checkin.dto;

import io.swagger.v3.oas.annotations.media.Schema;

// Response DTO
@Schema(description = "Response returned after a successful check-in")
public class CheckInResponse {

    @Schema(description = "Check-in ID", example = "1001")
    private Long checkInId;

    @Schema(description = "Check-in confirmation message", example = "Check-in successful")
    private String message;

    @Schema(description = "Seat number assigned", example = "12A")
    private String seatNumber;

    @Schema(description = "URL to download the boarding pass", example = "http://example.com/boarding-pass/1001")
    private String boardingPassUrl;

    public CheckInResponse() {
        // No-arg constructor for deserialization
    }

    public CheckInResponse(Long checkInId, String message, String seatNumber, String boardingPassUrl) {
        this.checkInId = checkInId;
        this.message = message;
        this.seatNumber = seatNumber;
        this.boardingPassUrl = boardingPassUrl;
    }
    public String getBoardingPassUrl() {
        return boardingPassUrl;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public String getMessage() {
        return message;
    }

    public Long getCheckInId() {
        return checkInId;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setSeatNumber(String seatNumber) {
        this.seatNumber = seatNumber;
    }

    public void setBoardingPassUrl(String boardingPassUrl) {
        this.boardingPassUrl = boardingPassUrl;
    }

    public void setCheckInId(Long checkInId) {
        this.checkInId = checkInId;
    }
}
