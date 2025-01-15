package store.carjava.marketplace.app.payment.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import store.carjava.marketplace.app.payment.dto.PaymentRequest;
import store.carjava.marketplace.app.payment.dto.PaymentResponse;
import store.carjava.marketplace.app.payment.exception.PaymentBodyNullException;
import store.carjava.marketplace.app.payment.exception.PaymentClientErrorException;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Map;

@Service
public class PaymentService {

    @Value("${payment.secret}")
    private String WIDGET_SECRET_KEY;

    public PaymentResponse processPaymentConfirmation(PaymentRequest request) {
        try {
            // Toss Payment API 호출을 위한 restTemplate 생성
            RestTemplate restTemplate = new RestTemplate();

            // HTTP 요청 헤더 생성
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", createAuthorizationHeader());
            headers.setContentType(MediaType.APPLICATION_JSON);

            // API 호출 바디 설정
            Map<String, String> requestBody = Map.of(
                    "orderId", request.orderId(),
                    "amount", request.amount(),
                    "paymentKey", request.paymentKey()
            );

            // API 호출을 위한 requestEntity 생성
            HttpEntity<Map<String, String>> requestEntity = new HttpEntity<>(requestBody, headers);

            // Toss Payment API 호출
            ResponseEntity<Map> tossPaymentApiResponse = restTemplate.postForEntity(
                    "https://api.tosspayments.com/v1/payments/confirm",
                    requestEntity,
                    Map.class
            );

            // API 응답을 map 객체로 변환
            Map<String, Object> response = tossPaymentApiResponse.getBody();
            if (response == null) { // 응답 본문이 null 인 경우 = toss payment api가 오류인 경우
                throw new PaymentBodyNullException();
            }

            return PaymentResponse.from(response);

        } catch (HttpClientErrorException e) {
            throw new PaymentClientErrorException();
        }
    }

    private String createAuthorizationHeader() {
        Base64.Encoder encoder = Base64.getEncoder();
        byte[] encodedBytes = encoder.encode((WIDGET_SECRET_KEY + ":").getBytes(StandardCharsets.UTF_8));
        return "Basic " + new String(encodedBytes);
    }
}


