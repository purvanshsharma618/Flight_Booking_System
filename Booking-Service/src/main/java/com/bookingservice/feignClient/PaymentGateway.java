package com.bookingservice.feignClient;

import com.bookingservice.dto.BookingRequest;
import com.bookingservice.dto.StripeResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Service
@FeignClient("STRIPEGATEWAY")
public interface PaymentGateway {

    @PostMapping("/payment/checkout")
    StripeResponse checkoutProducts(@RequestBody BookingRequest bookingRequest);
}
