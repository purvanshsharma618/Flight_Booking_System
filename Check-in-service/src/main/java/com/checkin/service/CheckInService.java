package com.checkin.service;


import com.checkin.dto.BookingDto;
import com.checkin.dto.CheckInResponse;
import com.checkin.feignClient.BookingService;
import com.checkin.model.CheckIn;
import com.checkin.dto.CheckInRequest;
import com.checkin.repository.CheckInRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.client.HttpClientErrorException;

import java.time.LocalDateTime;
import java.util.*;
        import java.util.stream.Collectors;

@Slf4j
@Service
public class CheckInService {

    @Autowired
    private BookingService bookingService;

    @Autowired
    private CheckInRepository checkInRepository;

    private static final int[] seats = new int[40];

    public ResponseEntity<String> processCheckIn(CheckIn request) {
        String bookingId = request.getBookingId();
        int seatNumber = request.getSeatNumber();

        List<String> bookings = bookingService.getAllBookingIds().getBody(); // Moved here

        if (!bookings.contains(bookingId)) {
            return ResponseEntity.badRequest().body("Invalid Booking ID");
        }

        if (checkInRepository.findBySeatNumber(seatNumber).isPresent()) {
            return ResponseEntity.badRequest().body("Seat already booked");
        }
        checkInRepository.findAll().forEach(System.out::println);

        if (checkInRepository.findByBookingId(bookingId).isPresent()) {
            return ResponseEntity.badRequest().body("This booking ID has already checked in");
        }

        CheckIn checkIn = new CheckIn();
        checkIn.setBookingId(bookingId);
        checkIn.setSeatNumber(seatNumber);

        checkInRepository.save(checkIn);
        System.out.println(">>> Check-in success. Seat: " + seatNumber);
        return ResponseEntity.ok("Checked in successfully. Seat: " + seatNumber);
    }


    public Optional<CheckIn> findBySeatNumber(int seatNumber) {
        return checkInRepository.findBySeatNumber(seatNumber);
    }

    public Optional<CheckIn> findByBookingId(String bookingId) {
        return checkInRepository.findByBookingId(bookingId);
    }
}

//    @Override
//    public CheckInResponse checkIn(CheckInRequest request) {
//
//        // 1. Validate booking exists
//        try {
//            ResponseEntity<BookingDto> bookingResponse = bookingService.getBookingById(request.getBookingId().toString());
//
//            if (bookingResponse.getBody() == null || !bookingResponse.getBody().getBookingId()) {
//                throw new RuntimeException("Booking is not confirmed or invalid.");
//            }
//        } catch (HttpClientErrorException e) {
//            throw new RuntimeException("Booking not found.");
//        }
//
//        // 2. Validate flight exists
//        try {
//            restTemplate.getForObject(FLIGHT_SERVICE_URL + request.getFlightId(), String.class);
//        } catch (HttpClientErrorException e) {
//            throw new RuntimeException("Flight not found.");
//        }
//
//        // 3. Validate user exists
//        try {
//            restTemplate.getForObject(USER_SERVICE_URL + request.getUserId(), String.class);
//        } catch (HttpClientErrorException e) {
//            throw new RuntimeException("User not found.");
//        }
//        CheckIn checkIn = new CheckIn();
//        checkIn.setBookingId(request.getBookingId());
//        checkIn.setFlightId(request.getFlightId());
//        checkIn.setUserId(request.getUserId());
//        checkIn.setSeatNumber(request.getSeatNumber());
//        checkIn.setCheckInTime(LocalDateTime.now());
//        checkIn.setCheckedIn(true);
//
//        checkIn = checkInRepository.save(checkIn);
//
//        CheckInRequest.CheckInResponse response = new CheckInRequest.CheckInResponse();
//        response.setCheckInId(checkIn.getId());
//        response.setMessage("Check-in successful");
//        response.setSeatNumber(checkIn.getSeatNumber());
//        response.setBoardingPassUrl("http://localhost:8082/api/checkin/" + checkIn.getId() + "/boarding-pass");
//
//        return response;
//    }
//
//    @Override
//    public CheckIn getCheckInById(Long id) {
//        return checkInRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("Check-in not found with ID: " + id));
//    }
//
//    @Override
//    public List<String> getAvailableSeatsForFlight(Long flightId) {
//        // Mock seat data (ideally fetched from Flight Service)
//        List<String> allSeats = Arrays.asList("1A", "1B", "1C", "2A", "2B", "2C");
//        List<String> takenSeats = checkInRepository.findSeatNumbersByFlightId(flightId);
//        return allSeats.stream()
//                .filter(seat -> !takenSeats.contains(seat))
//                .collect(Collectors.toList());
//    }
//
//    @Override
//    public byte[] generateBoardingPass(Long checkInId) {
//        // TODO: Generate PDF or image byte[] (using iText or similar)
//        return new byte[0];
//    }
//
//    @Override
//    public void cancelCheckIn(Long checkInId) {
//        CheckIn checkIn = getCheckInById(checkInId);
//        checkIn.setCheckedIn(false);
//        checkInRepository.save(checkIn);
//    }
//}

