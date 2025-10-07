package com.example.krishisaathi.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api")
public class FertilizerController {

    @Autowired
    private RestTemplate restTemplate;

    @PostMapping("/predictFertilizer")
    public ResponseEntity<?> predictFertilizer(@RequestBody Map<String, Object> data) {
        String pythonApiUrl = "http://localhost:8000/predictFertilizer"; // FastAPI endpoint
        try {
            ResponseEntity<Map> response = restTemplate.postForEntity(pythonApiUrl, data, Map.class);
            return ResponseEntity.ok(response.getBody());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Failed to predict fertilizer"));
        }
    }
}
