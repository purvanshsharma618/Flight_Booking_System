package com.checkin.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
        import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;


@Entity
@Table(name = "checkins")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Schema(description = "Entity representing a check-in record for a flight booking")
public class CheckIn {

    @Id
    @Schema(description = "Unique check-in ID (auto-generated 8-character string)", example = "a1b2c3d4")
    private String id;

    @Column(nullable = false, unique = true)
    @Schema(description = "Associated booking ID", example = "BKG123456")
    private String bookingId;

    @Column(nullable = false, unique = true)
    @Schema(description = "Associated booking ID", example = "BKG123456")
    private String flightId;

    @Column(nullable = false, unique = true)
    @Schema(description = "Seat number assigned during check-in", example = "12")
    private int seatNumber;

    @PrePersist
    public void generateId() {
        if (this.id == null) {
            this.id = UUID.randomUUID().toString()
                    .replace("-", "")
                    .substring(0, 8);  // Generates an 8-char ID
        }
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public int getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(int seatNumber) {
        this.seatNumber = seatNumber;
    }
    @Override
    public String toString() {
        return "CheckIn{" +
                "id='" + id + '\'' +
                ", bookingId='" + bookingId + '\'' +
                ", seatNumber=" + seatNumber +
                '}';
    }

}
