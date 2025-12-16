package com.weather.advisory;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "subscriptions")
public class Subscription {
    @Id
    private String id;
    private String phoneNumber;
    private String county;
    private boolean isActive;
    private LocalDateTime subscribedAt;

    public Subscription() {}

    public Subscription(String id, String phoneNumber, String county, boolean isActive, LocalDateTime subscribedAt) {
        this.id = id;
        this.phoneNumber = phoneNumber;
        this.county = county;
        this.isActive = isActive;
        this.subscribedAt = subscribedAt;
    }

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public String getCounty() { return county; }
    public void setCounty(String county) { this.county = county; }

    public boolean isActive() { return isActive; }
    public void setActive(boolean active) { isActive = active; }

    public LocalDateTime getSubscribedAt() { return subscribedAt; }
    public void setSubscribedAt(LocalDateTime subscribedAt) { this.subscribedAt = subscribedAt; }
}

