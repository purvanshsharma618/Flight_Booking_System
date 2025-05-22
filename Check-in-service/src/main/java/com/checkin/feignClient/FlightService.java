package com.checkin.feignClient;

import com.checkin.feignClient.feignClientConfig.FeignClientConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(
        name = "FLIGHT-SERVICE",
        configuration = FeignClientConfig.class
)
public interface FlightService {

    @GetMapping("/api/flights/{flightId}/seats")
    public ResponseEntity<List<Integer>> getAvailableSeats(@PathVariable String flightId);

    @PutMapping("/api/flights/{flightId}/seats")
    public ResponseEntity<String> updateAvailableSeats(
            @PathVariable String flightId,
            @RequestBody List<Integer> newSeats );
}

