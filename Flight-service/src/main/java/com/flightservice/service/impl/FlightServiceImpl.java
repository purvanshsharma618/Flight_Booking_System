package com.flightservice.service.impl;

import com.flightservice.model.Flight;
import com.flightservice.repository.FlightRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class FlightServiceImpl{

    @Autowired
    private FlightRepo flightRepository;

    public Flight addFlight(Flight flight) {

//        UUID id= UUID.randomUUID();
//        flight.setFlightId(id.toString());
        return flightRepository.save(flight);
    }


    public List<Flight> getAllFlights() {
        return flightRepository.findAll();
    }


    public Flight getFlightById(String id) {
        return flightRepository.findById(id).orElseThrow(()->new RuntimeException("UserId not found"));
    }


    public Flight updateFlight(Flight updatedFlight) {
        return flightRepository.save(updatedFlight);
    }

    public List<Flight> getFlightsByRoute(String fromCity, String toCity) {
        // Instead of using streams, manually iterate over the results
        List<Flight> allFlights = flightRepository.findAll();
        List<Flight> matchingFlights = new ArrayList<>();

        for (Flight flight : allFlights) {
            if (flight.getFromCity().equalsIgnoreCase(fromCity) && flight.getToCity().equalsIgnoreCase(toCity)) {
                matchingFlights.add(flight);
            }
        }

        return matchingFlights;
    }

    public List<Integer> getAvailableSeats(String flightId) {
        Flight flight = getFlightById(flightId);
        return flight.getAvailableSeats();
    }

    public void updateAvailableSeats(String flightId, List<Integer> newSeats) {
        Flight flight = getFlightById(flightId);
        flight.setAvailableSeats(newSeats);
        flightRepository.save(flight);
    }
    public void deleteFlight(String id) {
        flightRepository.deleteById(id);
    }
}

