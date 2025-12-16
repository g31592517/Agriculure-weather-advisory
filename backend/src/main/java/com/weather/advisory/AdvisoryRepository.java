package com.weather.advisory;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdvisoryRepository extends MongoRepository<Advisory, String> {
    Optional<Advisory> findTopByCountyOrderByCreatedAtDesc(String county);
}

