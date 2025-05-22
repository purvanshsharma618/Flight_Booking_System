package com.checkin.feignClient;

import com.checkin.dto.BookingDto;
import com.checkin.dto.Email;
import com.checkin.feignClient.feignClientConfig.FeignClientConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name="NOTIFICATION-SERVICE")
public interface NotificationService {

    @PostMapping("/sendmail")
    public String message(@RequestBody Email email);
}