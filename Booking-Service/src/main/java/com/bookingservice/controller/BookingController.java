package com.bookingservice.controller;

import com.bookingservice.dto.BookingRequest;
import com.bookingservice.dto.ConfirmBookingRequest;
import com.bookingservice.dto.Email;
import com.bookingservice.dto.StripeResponse;
import com.bookingservice.exceptions.ResourceNotFoundException;
import com.bookingservice.feignClient.NotificationService;
import com.bookingservice.feignClient.PaymentGateway;
import com.bookingservice.model.BookingEntity;
//import com.flightbooking.BookingService.repository.BookingRepository;
import com.bookingservice.security.JwtService;
import com.bookingservice.service.IBookingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/api/bookings")


@Tag(name = "Booking Controller", description = "Endpoints for managing flight bookings")
public class BookingController {

    @Autowired
    private PaymentGateway paymentGateway;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private IBookingService bookingService;

    @Autowired
    private JwtService jwtService;




    @Operation(summary = "Create a new booking with payment")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @PostMapping("/book")
    public ResponseEntity<StripeResponse> createBooking(@RequestBody BookingEntity booking) {
        BookingRequest request = new BookingRequest();
        request.setName(booking.getName());
        request.setAmount(booking.getPaymentAmount());
        request.setQuantity(1L);
        request.setCurrency("INR");

        StripeResponse stripe = paymentGateway.checkoutProducts(request);
        if (stripe == null || !"success".equalsIgnoreCase(stripe.getStatus())) {
            System.out.println(stripe.getStatus());
            throw new RuntimeException("Payment unsuccessful, booking not created.");
        }

        booking.setPaymentId(stripe.getSessionId());
        bookingService.createBooking(booking);

        Email email = new Email();
        email.setTo(booking.getEmail());
        email.setSubject("Flight Booking Confirmation");
        email.setBody("Dear " + booking.getName() + ",\n\n" +
                "Thank you for booking with us! Your flight has been successfully booked.\n\n" +
                "Booking Details:\n" +
                "Booking ID   : " + booking.getBookingId() + "\n" +
                "Flight ID    : " + booking.getFlightId() + "\n" +
                "Amount Paid  : " + booking.getPaymentAmount() + " INR\n\n" +
                "We wish you a pleasant journey!\n\n" +
                "Best regards,\n" +
                "Flight Reservation Team");

        notificationService.message(email);
        return ResponseEntity.status(HttpStatus.CREATED).body(stripe);
    }

    @Operation(summary = "Retrieve all bookings (Admin only)")
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<BookingEntity>> getAllBookings() {
        return ResponseEntity.ok(bookingService.getAllBookings());
    }

    @Operation(summary = "Retrieve all booking IDs (Admin only)")
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/ids")
    public ResponseEntity<List<String>> getAllBookingIds() {
        return ResponseEntity.ok(bookingService.getAllBookingID());
    }

    @Operation(summary = "Retrieve booking by ID (User/Admin)")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @GetMapping("/{id}")
    public ResponseEntity<?> getBookingById(@PathVariable String id, @RequestHeader("Authorization") String token) {

        if (token == null || !token.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token");
        }


        try {
            String userId = jwtService.extractUserId(token.substring(7));
            String role= jwtService.extractRole(token.substring(7));
            BookingEntity booking;
            if(role.equals("ADMIN")){
                booking = bookingService.getBookingById(id);
            }else {
                booking = bookingService.getBookingByIdForUser(id, userId);
            }
            return ResponseEntity.ok(booking);
        } catch (AccessDeniedException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Unauthorized access");
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @Operation(summary = "Update a booking by ID (User/Admin)")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @PutMapping("/{id}")
    public ResponseEntity<?> updateBooking(@PathVariable String id, @RequestBody BookingEntity updatedBooking, HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if (token == null || !token.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        try {
            String userId = jwtService.extractUserId(token.substring(7)).toString();
            BookingEntity booking = bookingService.updateBookingForUser(id, updatedBooking, userId);
            Email email = new Email();
            email.setTo(booking.getEmail());
            email.setSubject("Flight Booking Update Confirmation");
            email.setBody("Dear " + booking.getName() + ",\n\n" +
                    "Your flight booking has been successfully updated.\n\n" +
                    "Updated Booking Details:\n" +
                    "Booking ID   : " + booking.getBookingId() + "\n" +
                    "Flight ID    : " + booking.getFlightId() + "\n" +
                    "Amount Paid  : " + booking.getPaymentAmount() + " INR\n\n" +
                    "If you did not request this update or need assistance, please contact our support team.\n\n" +
                    "Thank you,\n" +
                    "Flight Reservation Team");
            notificationService.message(email);
            return ResponseEntity.ok(booking);
        } catch (AccessDeniedException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Unauthorized update");
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @Operation(summary = "Delete a booking by ID (User/Admin)")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBooking(@PathVariable String id, HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if (token == null || !token.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        try {
            String userId = jwtService.extractUserId(token.substring(7)).toString();
            BookingEntity booking = bookingService.deleteBookingForUser(id, userId);
            Email email = new Email();
            email.setTo(booking.getEmail());
            email.setSubject("Flight Booking Cancellation Notice");
            email.setBody("Dear " + booking.getName() + ",\n\n" +
                    "We would like to inform you that your flight booking has been successfully cancelled.\n\n" +
                    "Booking Details:\n" +
                    "Booking ID: " + booking.getBookingId() + "\n" +
                    "Flight ID: " + booking.getFlightId() + "\n" +
                    "Amount Paid: " + booking.getPaymentAmount() + "\n\n" +
                    "If you did not request this cancellation or have any concerns, please contact our support team immediately.\n\n" +
                    "Thank you for using our service,\n" +
                    "Flight Reservation Team");
            notificationService.message(email);
            return ResponseEntity.ok("Booking with ID " + id + " deleted successfully.");
        } catch (AccessDeniedException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Unauthorized delete");
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}


