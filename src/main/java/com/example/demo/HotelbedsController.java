package com.example.demo;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.client.LaxRedirectStrategy;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;

@RestController
@RequestMapping("/api")
public class HotelbedsController {
    private static final Logger logger = LoggerFactory.getLogger(HotelbedsController.class);
    private static final String API_KEY = "HOTELBEDS_HOTEL_API_KEY";
    private static final String SECRET = "HOTELBEDS_HOTEL_SECRET";
    private static final String URL = "https://api.test.hotelbeds.com/hotel-api/v1/hotels";

    @PostMapping("/hotelbeds-hotels-booking-hotel-availability")
    public ResponseEntity<String> getHotelAvailability(@RequestBody String requestBody) {
        logger.info("Request Body: " + requestBody);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Accept", "application/json");
        headers.add("Api-key", API_KEY);
        headers.add("X-Signature", generateSignature());
        headers.add("Accept-Encoding", "gzip");
        headers.add("Content-Type", "application/json");

        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

        CloseableHttpClient httpClient = HttpClients.custom()
                .setRetryHandler(new DefaultHttpRequestRetryHandler(3, true))
                .setRedirectStrategy(new LaxRedirectStrategy())
                .build();

        RestTemplate restTemplate = new RestTemplate(new HttpComponentsClientHttpRequestFactory(httpClient));
        ((HttpComponentsClientHttpRequestFactory) restTemplate.getRequestFactory()).setConnectTimeout(60000);
        ((HttpComponentsClientHttpRequestFactory) restTemplate.getRequestFactory()).setReadTimeout(60000);

        try {
            ResponseEntity<String> response = restTemplate.exchange(URL, HttpMethod.POST, entity, String.class);
            logger.info("Response Body: " + response.getBody());
            return response;
        } catch (HttpClientErrorException | ResourceAccessException e) {
            logger.error("Error occurred: " + e.getMessage());
            return ResponseEntity.status(e.getStatusCode()).body(e.getResponseBodyAsString());
        }
    }

    private String generateSignature() {
        String signature = API_KEY + SECRET + Instant.now().getEpochSecond();
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(signature.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error generating signature", e);
        }
    }
}