package store.carjava.marketplace.app.payment.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import store.carjava.marketplace.app.car_purchase_history.repository.CarPurchaseHistoryRepository;
import store.carjava.marketplace.app.payment.dto.PaymentRequest;
import store.carjava.marketplace.app.payment.dto.PaymentResponse;
import store.carjava.marketplace.app.payment.exception.PaymentBodyNullException;
import store.carjava.marketplace.app.payment.exception.PaymentClientErrorException;
import store.carjava.marketplace.app.user.entity.User;
import store.carjava.marketplace.common.util.user.UserResolver;

import java.nio.charset.StandardCharsets;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final static String TOSS_API_URL = "https://api.tosspayments.com/v1/payments/confirm";

    @Value("${payment.secret}")
    private String WIDGET_SECRET_KEY;

    private final CarPurchaseHistoryRepository purchaseHistoryRepository;
    private final UserResolver userResolver;

    public PaymentResponse processPaymentConfirmation(PaymentRequest paymentRequest) {
        try {
            // Toss Payment API 호출을 위한 restTemplate 생성
            RestTemplate restTemplate = new RestTemplate();

            // API 요청 헤더 생성
            HttpHeaders headers = makeRequestHeaders();

            // API 호출 바디 설정
            Map<String, String> requestBody = makeRequestBody(paymentRequest);

            // API 호출을 위한 requestEntity 생성
            HttpEntity<Map<String, String>> requestEntity = new HttpEntity<>(requestBody, headers);

            // Toss Payment API 호출
            ResponseEntity<Map> tossPaymentApiResponse = restTemplate.postForEntity(
                    TOSS_API_URL,
                    requestEntity,
                    Map.class
            );

            // API 응답을 map 객체로 변환
            Map<String, Object> apiResponse = tossPaymentApiResponse.getBody();
            if (apiResponse == null) { // 응답 본문이 null 인 경우 = toss payment api가 오류인 경우
                throw new PaymentBodyNullException();
            }

            // CarPurchaseHistory 생성
            makeCarPurchaseHistory(apiResponse);

            // Response 객체 생성
            PaymentResponse paymentResponse = makePaymentResponse(apiResponse);

            return paymentResponse;
        } catch (HttpClientErrorException e) {
            throw new PaymentClientErrorException();
        }
    }

    private Map<String, String> makeRequestBody(PaymentRequest request) {
        return Map.of(
                "orderId", request.orderId(),
                "amount", request.amount(),
                "paymentKey", request.paymentKey()
        );
    }

    private HttpHeaders makeRequestHeaders() {
        HttpHeaders headers = new HttpHeaders();

        headers.set("Authorization", createAuthorizationHeader());
        headers.setContentType(MediaType.APPLICATION_JSON);

        return headers;
    }

    private void makeCarPurchaseHistory(Map<String, Object> apiResponse) {
        // 현재 결제중인 유저 정보 확인
        User user = userResolver.getCurrentUser();


    }

    private PaymentResponse makePaymentResponse(Map<String, Object> apiResponse) {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME;

        Map<String, Object> easyPay = (Map<String, Object>) apiResponse.get("easyPay");
        return new PaymentResponse(
                (String) apiResponse.get("orderId"),
                (String) apiResponse.get("orderName"),
                OffsetDateTime.parse((String) apiResponse.get("requestedAt"), formatter).toLocalDateTime(),
                OffsetDateTime.parse((String) apiResponse.get("approvedAt"), formatter).toLocalDateTime(),
                (String) apiResponse.get("currency"),
                ((Number) apiResponse.get("totalAmount")).longValue(),
                ((Number) apiResponse.get("suppliedAmount")).longValue(),
                ((Number) apiResponse.get("vat")).longValue(),
                easyPay != null ? (String) easyPay.get("provider") : null
        );
    }

    private String createAuthorizationHeader() {
        Base64.Encoder encoder = Base64.getEncoder();
        byte[] encodedBytes = encoder.encode((WIDGET_SECRET_KEY + ":").getBytes(StandardCharsets.UTF_8));
        return "Basic " + new String(encodedBytes);
    }
}


