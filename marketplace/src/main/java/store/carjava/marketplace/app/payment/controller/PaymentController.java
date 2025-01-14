package store.carjava.marketplace.app.payment.controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.json.JSONParser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/payment")
public class PaymentController {

    @Value("${payment.secret}")
    private String WIDGET_SECRET_KEY;

    @PostMapping("/confirm")
    public ResponseEntity<Map<String, Object>> confirmPayment(@RequestBody Map<String, String> requestData) {
        String paymentKey = requestData.get("paymentKey");
        String orderId = requestData.get("orderId");
        String amount = requestData.get("amount");

        log.info("paymentKey : {}", paymentKey);
        log.info("orderId : {}", orderId);
        log.info("amount : {}", amount);

        String authorizations = createAuthorizationHeader();

        try {
            // HTTP 요청 생성
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", authorizations);
            headers.setContentType(MediaType.APPLICATION_JSON);

            Map<String, String> requestBody = Map.of("orderId", orderId, "amount", amount, "paymentKey", paymentKey);
            HttpEntity<Map<String, String>> requestEntity = new HttpEntity<>(requestBody, headers);

            // API 호출
            ResponseEntity<Map> response = restTemplate.postForEntity(
                    "https://api.tosspayments.com/v1/payments/confirm",
                    requestEntity,
                    Map.class
            );

            log.info(response.getBody().toString());

            return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
        } catch (HttpClientErrorException e) {
            log.error("Error during payment confirmation: {}", e.getMessage());
            return ResponseEntity.status(e.getStatusCode()).body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            log.error("Unexpected error: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", "Internal server error"));
        }
    }

    private String createAuthorizationHeader() {
        Base64.Encoder encoder = Base64.getEncoder();
        byte[] encodedBytes = encoder.encode((WIDGET_SECRET_KEY + ":").getBytes(StandardCharsets.UTF_8));
        return "Basic " + new String(encodedBytes);
    }
}
