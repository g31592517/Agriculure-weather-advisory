package com.weather.advisory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class SubscriptionService {
    private static final Logger log = LoggerFactory.getLogger(SubscriptionService.class);

    private final SubscriptionRepository subscriptionRepository;

    public SubscriptionService(SubscriptionRepository subscriptionRepository) {
        this.subscriptionRepository = subscriptionRepository;
    }

    public Subscription subscribe(String phoneNumber, String county) {
        // Validate Kenyan phone number
        if (!isValidKenyanPhone(phoneNumber)) {
            throw new IllegalArgumentException("Invalid Kenyan phone number. Expected format: 07XX or 01XX or +254");
        }

        // Check for existing subscription
        Optional<Subscription> existing = subscriptionRepository.findByPhoneNumberAndCounty(phoneNumber, county);
        if (existing.isPresent() && existing.get().isActive()) {
            log.info("User {} already subscribed to {}", phoneNumber, county);
            return existing.get();
        }

        // Create new subscription
        Subscription subscription = new Subscription();
        subscription.setPhoneNumber(phoneNumber);
        subscription.setCounty(county);
        subscription.setActive(true);
        subscription.setSubscribedAt(LocalDateTime.now());

        Subscription saved = subscriptionRepository.save(subscription);

        // Simulate SMS sending
        simulateSendSMS(phoneNumber, "You are now subscribed to weather alerts for " + county + " county.");

        log.info("Successfully subscribed {} to {} county", phoneNumber, county);
        return saved;
    }

    public void unsubscribe(String phoneNumber, String county) {
        Optional<Subscription> subscription = subscriptionRepository.findByPhoneNumberAndCounty(phoneNumber, county);

        if (subscription.isEmpty()) {
            throw new IllegalArgumentException("No subscription found for this phone number and county");
        }

        Subscription sub = subscription.get();
        sub.setActive(false);
        subscriptionRepository.save(sub);

        // Simulate SMS sending
        simulateSendSMS(phoneNumber, "You have been unsubscribed from weather alerts for " + county + " county.");

        log.info("Successfully unsubscribed {} from {} county", phoneNumber, county);
    }

    private boolean isValidKenyanPhone(String phone) {
        // Remove spaces and validate Kenyan phone format
        String cleaned = phone.replaceAll("\\s", "");
        // Matches: 0712345678, 0112345678, or +254712345678
        return cleaned.matches("^(\\+254|0)[17]\\d{8}$");
    }

    private void simulateSendSMS(String phoneNumber, String message) {
        // SMS simulation - logs the message instead of actually sending
        log.info("SMS SIMULATION");
        log.info("To: {}", phoneNumber);
        log.info("Message: {}", message);
        log.info("Status: SMS would be sent via Africa's Talking or similar service");
        log.info("=".repeat(50));

        // TODO: Integrate with actual SMS provider (Africa's Talking, Twilio, etc.)
        // Example for Africa's Talking:
        // afrikasTalkingClient.sendSMS(phoneNumber, message);
    }
}

