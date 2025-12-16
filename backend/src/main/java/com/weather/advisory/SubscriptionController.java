package com.weather.advisory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:3000", "http://localhost:8081"})
public class SubscriptionController {
    private static final Logger log = LoggerFactory.getLogger(SubscriptionController.class);

    private final SubscriptionService subscriptionService;

    public SubscriptionController(SubscriptionService subscriptionService) {
        this.subscriptionService = subscriptionService;
    }

    @PostMapping("/subscribe")
    public ResponseEntity<?> subscribe(@Valid @RequestBody SubscriptionRequest request) {
        log.info("Subscription request: {} for county {}", request.getPhoneNumber(), request.getCounty());
        try {
            Subscription subscription = subscriptionService.subscribe(
                request.getPhoneNumber(),
                request.getCounty()
            );
            return ResponseEntity.ok(new SubscriptionResponse("success",
                "Successfully subscribed to " + request.getCounty() + " alerts"));
        } catch (IllegalArgumentException e) {
            log.error("Invalid subscription request: {}", e.getMessage());
            return ResponseEntity.badRequest()
                .body(new SubscriptionResponse("error", e.getMessage()));
        } catch (Exception e) {
            log.error("Error processing subscription: {}", e.getMessage());
            return ResponseEntity.internalServerError()
                .body(new SubscriptionResponse("error", "Failed to process subscription"));
        }
    }

    @PostMapping("/unsubscribe")
    public ResponseEntity<?> unsubscribe(@Valid @RequestBody UnsubscribeRequest request) {
        log.info("Unsubscribe request: {} from county {}", request.getPhoneNumber(), request.getCounty());
        try {
            subscriptionService.unsubscribe(request.getPhoneNumber(), request.getCounty());
            return ResponseEntity.ok(new SubscriptionResponse("success",
                "Successfully unsubscribed from " + request.getCounty() + " alerts"));
        } catch (IllegalArgumentException e) {
            log.error("Invalid unsubscribe request: {}", e.getMessage());
            return ResponseEntity.badRequest()
                .body(new SubscriptionResponse("error", e.getMessage()));
        } catch (Exception e) {
            log.error("Error processing unsubscribe: {}", e.getMessage());
            return ResponseEntity.internalServerError()
                .body(new SubscriptionResponse("error", "Failed to process unsubscribe"));
        }
    }

    public static class SubscriptionRequest {
        @NotBlank(message = "Phone number is required")
        private String phoneNumber;

        @NotBlank(message = "County is required")
        private String county;

        public String getPhoneNumber() { return phoneNumber; }
        public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

        public String getCounty() { return county; }
        public void setCounty(String county) { this.county = county; }
    }

    public static class UnsubscribeRequest {
        @NotBlank(message = "Phone number is required")
        private String phoneNumber;

        @NotBlank(message = "County is required")
        private String county;

        public String getPhoneNumber() { return phoneNumber; }
        public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

        public String getCounty() { return county; }
        public void setCounty(String county) { this.county = county; }
    }

    public static class SubscriptionResponse {
        private String status;
        private String message;

        public SubscriptionResponse() {}

        public SubscriptionResponse(String status, String message) {
            this.status = status;
            this.message = message;
        }

        public String getStatus() { return status; }
        public void setStatus(String status) { this.status = status; }

        public String getMessage() { return message; }
        public void setMessage(String message) { this.message = message; }
    }
}

