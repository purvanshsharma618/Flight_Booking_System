package com.checkin.service;

import com.checkin.dto.CheckInResponse;
import com.checkin.model.CheckIn;
import com.checkin.dto.CheckInRequest;

import java.util.List;


public interface CheckInServiceInterface {
    CheckInResponse checkIn(CheckInRequest request);
    CheckIn getCheckInById(Long id);
    List<String> getAvailableSeatsForFlight(Long flightId);
    byte[] generateBoardingPass(Long checkInId); // Optional: Return PDF/image as byte[]
    void cancelCheckIn(Long checkInId);
}