package com.flightservice.controller;

import com.flightservice.feign.FareServiceClient;
import com.flightservice.model.FareRequest;
import com.flightservice.model.Flight;
import com.flightservice.service.impl.FlightServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/flights")
@Tag(name = "Flight Controller", description = "Endpoints for managing flight details and fares")
public class FlightController {

    @Autowired
    private FareServiceClient fareServiceClient;

    @Autowired
    private FlightServiceImpl flightService;

    @Operation(summary = "Add a new flight", description = "Admin only. Adds a new flight and calls Fare service to attach fare details.")
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/addNewFlight")
    public ResponseEntity<Flight> addFlight(@RequestBody Flight flight) {
        Flight savedFlight = flightService.addFlight(flight);

        FareRequest fareRequest = flight.getFareRequest();
        fareRequest.setFlightId(savedFlight.getFlightId());

        try {
            fareServiceClient.addFare(fareRequest);
        } catch (Exception e) {
            System.out.println("Fare service not working: " + e.getMessage());
        }

        return ResponseEntity.ok(savedFlight);
    }

    @Operation(summary = "Get all flights with fare details", description = "Accessible by User and Admin.")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @GetMapping("/all")
    public ResponseEntity<List<Flight>> getAllFlights() {
        List<Flight> flights = flightService.getAllFlights().stream().map(flight -> {
            try {
                FareRequest fare = fareServiceClient.getFareByFlightId(flight.getFlightId());
                flight.setFareRequest(fare);
            } catch (Exception e) {
                System.out.println("Could not fetch fare for flightId: " + flight.getFlightId());
            }
            return flight;
        }).collect(Collectors.toList());

        return new ResponseEntity<>(flights, HttpStatus.OK);
    }
    @Operation(
            summary = "Get flights by source and destination",
            description = "Fetch flights based on fromCity and toCity. Accessible by USER and ADMIN."
    )
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @GetMapping("/search")
    public ResponseEntity<List<Flight>> getFlightsByRoute(
            @RequestParam String fromCity,
            @RequestParam String toCity) {

        List<Flight> flights = flightService.getFlightsByRoute(fromCity, toCity);

        // Manually map fare data to each flight
        for (Flight flight : flights) {
            try {
                FareRequest fare = fareServiceClient.getFareByFlightId(flight.getFlightId());
                flight.setFareRequest(fare);
            } catch (Exception e) {
                System.out.println("Could not fetch fare for flightId: " + flight.getFlightId());
            }
        }

        return new ResponseEntity<>(flights, HttpStatus.OK);
    }

    @Operation(summary = "Get flight by ID", description = "Accessible by User and Admin. Fetches flight and its fare.")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<Flight> getFlightById(@PathVariable String id) {
        Flight flight = flightService.getFlightById(id);
        FareRequest fare = fareServiceClient.getFareByFlightId(id);
        flight.setFareRequest(fare);
        return ResponseEntity.ok(flight);
    }

    @Operation(summary = "Update flight", description = "Admin only. Updates flight and fare information.")
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/admin/update")
    public ResponseEntity<Flight> updateFlight(@RequestBody Flight flight) {
        Flight updatedFlight = flightService.updateFlight(flight);

        FareRequest fareRequest = flight.getFareRequest();
        fareRequest.setFlightId(updatedFlight.getFlightId());

        fareServiceClient.updateFare(updatedFlight.getFlightId(), fareRequest);

        return new ResponseEntity<>(updatedFlight, HttpStatus.OK);
    }

    @Operation(summary = "Delete a flight", description = "Admin only. Deletes a flight and its fare.")
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/admin/delete/{id}")
    public ResponseEntity<String> deleteFlight(@PathVariable String id) {
        flightService.deleteFlight(id);
        fareServiceClient.deleteFare(id);
        return ResponseEntity.ok("Flight deleted successfully.");
    }


    @GetMapping("/{flightId}/seats")
    public ResponseEntity<List<Integer>> getAvailableSeats(@PathVariable String flightId) {
        try {
            List<Integer> seats = flightService.getAvailableSeats(flightId);
            return new ResponseEntity<>(seats, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{flightId}/seats")
    public ResponseEntity<String> updateAvailableSeats(
            @PathVariable String flightId,
            @RequestBody List<Integer> newSeats
    ) {
        try {
            flightService.updateAvailableSeats(flightId, newSeats);
            return new ResponseEntity<>("Available seats updated successfully", HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}

