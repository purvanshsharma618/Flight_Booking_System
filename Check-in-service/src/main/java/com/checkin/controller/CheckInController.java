package com.checkin.controller;

import com.checkin.dto.Email;
import com.checkin.feignClient.BookingService;
import com.checkin.feignClient.FlightService;
import com.checkin.feignClient.NotificationService;
import com.checkin.model.BookingEntity;
import com.checkin.model.CheckIn;
import com.checkin.service.CheckInService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/api/checkin")
public class CheckInController {

    @Autowired
    private CheckInService checkInService;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private BookingService bookingService;

    @Autowired
    private FlightService flightService;

    @Operation(summary = "Perform check-in for a booking", description = "Allows user or admin to perform check-in and sends confirmation email.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Check-in successful"),
            @ApiResponse(responseCode = "400", description = "Invalid request"),
            @ApiResponse(responseCode = "500", description = "Server error", content = @Content)
    })
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @PostMapping
    public ResponseEntity<String> checkIn(
            @RequestBody @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Check-in request payload",
                    required = true,
                    content = @Content(schema = @Schema(implementation = CheckIn.class))
            ) CheckIn request,

            @RequestHeader("Authorization")
            @Parameter(description = "JWT access token", required = true, example = "Bearer eyJhbGci...") String token
    ) {
        ResponseEntity<String> response = checkInService.processCheckIn(request);

        if (response.getStatusCode() == HttpStatus.OK) {
            BookingEntity booking;
            try {
                booking = bookingService.getBookingById(request.getBookingId().toString(), token).getBody();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            if (booking != null && !Objects.toString(booking.getEmail(), "").isEmpty()) {
                Email email = new Email();
                email.setTo(booking.getEmail());
                email.setSubject("Flight Check-In Confirmation");
                email.setBody("Dear " + booking.getName() + ",\n\n" +
                        "Your check-in has been successfully completed.\n\n" +
                        "Booking ID   : " + booking.getBookingId() + "\n" +
                        "Flight ID    : " + booking.getFlightId() + "\n" +
                        "Passenger    : " + booking.getName() + "\n\n" +
                        "Seat number  : " + request.getSeatNumber() + "\n\n" +
                        "Please arrive at the airport on time with valid ID proof.\n\n" +
                        "Thank you,\n" +
                        "Flight Reservation Team");
                notificationService.message(email);
            }
        }

        return response;
    }

    @Operation(summary = "Get check-in details by seat number", description = "Fetch check-in info for a specific seat.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Seat check-in found"),
            @ApiResponse(responseCode = "400", description = "Invalid seat number"),
            @ApiResponse(responseCode = "404", description = "Seat not booked")
    })
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @GetMapping("/seat/{seatNumber}")
    public ResponseEntity<?> getBySeatNumber(
            @Parameter(description = "Seat number to check (1 to 40)", example = "12")
            @PathVariable int seatNumber
    ) {
        if (seatNumber < 1 || seatNumber > 40) {
            return ResponseEntity.badRequest().body("Seat number should be between 1 and 40");
        }

        Optional<CheckIn> checkIn = checkInService.findBySeatNumber(seatNumber);
        return checkIn.isPresent()
                ? new ResponseEntity<>(checkIn, HttpStatus.OK)
                : new ResponseEntity<>("Seat not booked yet", HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "Get check-in details by booking ID", description = "Fetch check-in info for a given booking ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Check-in found or not found message returned"),
            @ApiResponse(responseCode = "400", description = "Invalid booking ID")
    })
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @GetMapping("/booking/{bookingId}")
    public ResponseEntity<?> getByBookingId(
            @Parameter(description = "Booking ID to check", example = "BKG123456")
            @PathVariable String bookingId
    ) {
        Optional<CheckIn> checkIn = checkInService.findByBookingId(bookingId);
        return checkIn.isPresent()
                ? new ResponseEntity<>(checkIn, HttpStatus.OK)
                : new ResponseEntity<>("No-check In found for this Booking Id", HttpStatus.OK);
    }
    @GetMapping("/seats/{flightId}")
    public ResponseEntity<List<Integer>> getAvailableSeats(@PathVariable String flightId) {
        return flightService.getAvailableSeats(flightId);
    }

    @PutMapping("/seats/{flightId}")
    public ResponseEntity<String> updateAvailableSeats(
            @PathVariable String flightId,
            @RequestBody Integer newSeat) {
        List<Integer>newSeats=flightService.getAvailableSeats(flightId).getBody();
        newSeats.add(newSeat-1,newSeat);
        return flightService.updateAvailableSeats(flightId, newSeats);
    }

}
