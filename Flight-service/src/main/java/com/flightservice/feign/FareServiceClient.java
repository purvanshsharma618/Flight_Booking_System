package com.flightservice.feign;

import com.flightservice.model.FareRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "FARE-SERVICE") // Name registered in Eureka or your config
public interface FareServiceClient {

    @PostMapping("/fare/addNewFare")
    String addFare(@RequestBody FareRequest fareRequest);

    @GetMapping("/fare/{id}")
    FareRequest getFareByFlightId(@PathVariable("id") String flightId);

    @PutMapping("/fare/updatefare/{id}")
    void updateFare(@PathVariable("id") String id, @RequestBody FareRequest fareRequest);

    @DeleteMapping("/fare/{id}")
    void deleteFare(@PathVariable("id") String id);
}

