package com.example.krishisaathi.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class DiseaseController {

    @Value("${python.api.url}")
    private String pythonApiUrl; // e.g., http://localhost:8000/predict

    private final RestTemplate restTemplate = new RestTemplate();

    @PostMapping("/predictDisease")
    public ResponseEntity<?> predictDisease(
            @RequestParam("crop") String crop,
            @RequestParam("image") MultipartFile image) {

        try {
            // ✅ Prepare request headers
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);
            headers.setAccept(List.of(MediaType.APPLICATION_JSON));

            // ✅ Prepare body (form-data)
            MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
            body.add("crop", crop);

            // ✅ Convert MultipartFile -> ByteArrayResource with filename
            ByteArrayResource imageResource = new ByteArrayResource(image.getBytes()) {
                @Override
                public String getFilename() {
                    return image.getOriginalFilename();
                }
            };
            body.add("image", imageResource);

            HttpEntity<MultiValueMap<String, Object>> requestEntity =
                    new HttpEntity<>(body, headers);

            // ✅ Send request to Python API
            ResponseEntity<Map> response =
                    restTemplate.postForEntity(pythonApiUrl, requestEntity, Map.class);

            // ✅ Return Python response to frontend
            return ResponseEntity.ok(response.getBody());

        } catch (Exception e) {
            e.printStackTrace();

            // ⚠️ Fallback (if Python server not reachable)
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                    .body(Map.of(
                            "error", "Could not connect to ML server",
                            "message", e.getMessage(),
                            "fallback", Map.of(
                                    "crop", crop,
                                    "disease", "Leaf Blight (Sample Prediction)"
                            )
                    ));
        }
    }
}
