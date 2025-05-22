package com.checkin.feignClient.feignClientConfig;

import feign.RequestInterceptor;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Configuration
public class FeignClientConfig {

    @Bean
    public RequestInterceptor requestInterceptor() {
        return requestTemplate -> {
            if (requestTemplate.feignTarget() != null &&
                    "NOTIFICATION-SERVICE".equalsIgnoreCase(requestTemplate.feignTarget().name())) {
                // Skip token forwarding for this service
                return;
            }
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
                    .getRequestAttributes()).getRequest();

            String authHeader = request.getHeader("Authorization");

            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                requestTemplate.header("Authorization", authHeader);
                System.out.println(">> Feign: Forwarding token = " + authHeader);
            } else {
                System.out.println(">> Feign: No token found in incoming request");
            }
        };
    }
}
