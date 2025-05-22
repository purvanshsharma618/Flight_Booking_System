package com.checkin.feignClient;

import com.checkin.feignClient.feignClientConfig.FeignClientConfig;
import com.checkin.model.BookingEntity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

@FeignClient(
        name = "BOOKING-SERVICE",
        configuration = FeignClientConfig.class
)
public interface BookingService {

    @GetMapping("/api/bookings/{id}")
    public ResponseEntity<BookingEntity> getBookingById(@PathVariable String id, @RequestHeader("Authorization") String token);

    @GetMapping("/api/bookings/ids")
    public ResponseEntity<List<String>> getAllBookingIds();
}

