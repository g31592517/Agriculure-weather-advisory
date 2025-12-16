package com.weather.advisory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/advisory")
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:3000", "http://localhost:8081"})
public class AdvisoryController {
    private static final Logger log = LoggerFactory.getLogger(AdvisoryController.class);

    private final AdvisoryService advisoryService;

    public AdvisoryController(AdvisoryService advisoryService) {
        this.advisoryService = advisoryService;
    }

    @GetMapping("/{county}")
    public ResponseEntity<Advisory> getAdvisory(@PathVariable String county) {
        log.info("Received request for advisory: {}", county);
        try {
            Advisory advisory = advisoryService.getOrCreateAdvisory(county);
            return ResponseEntity.ok(advisory);
        } catch (IllegalArgumentException e) {
            log.error("Invalid county: {}", county);
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            log.error("Error fetching advisory for {}: {}", county, e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }
}

