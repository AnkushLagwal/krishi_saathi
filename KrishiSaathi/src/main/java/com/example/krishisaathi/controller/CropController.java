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
public class CropController {

    @Autowired
    private RestTemplate restTemplate;

    @PostMapping("/predictCrop")
    public ResponseEntity<?> predictCrop(@RequestBody Map<String, Object> cropData) {
        String pythonApiUrl = "http://localhost:8000/predict";
        try {
            ResponseEntity<Map> response = restTemplate.postForEntity(pythonApiUrl, cropData, Map.class);
            Map<String, Object> body = response.getBody();

            // Optional: Just return recommended crops
            return ResponseEntity.ok(body);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Failed to predict crop"));
        }
    }


}

