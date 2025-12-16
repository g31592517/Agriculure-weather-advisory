package com.weather.advisory;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubscriptionRepository extends MongoRepository<Subscription, String> {
    Optional<Subscription> findByPhoneNumberAndCounty(String phoneNumber, String county);
    List<Subscription> findByCountyAndIsActiveTrue(String county);
}

