package store.carjava.marketplace.app.payment.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import store.carjava.marketplace.app.car_purchase_history.entity.CarPurchaseHistory;
import store.carjava.marketplace.app.car_purchase_history.repository.CarPurchaseHistoryRepository;
import store.carjava.marketplace.app.marketplace_car.entity.MarketplaceCar;
import store.carjava.marketplace.app.marketplace_car.exception.MarketplaceCarIdNotFoundException;
import store.carjava.marketplace.app.marketplace_car.repository.MarketplaceCarRepository;
import store.carjava.marketplace.app.payment.dto.PaymentRequest;
import store.carjava.marketplace.app.payment.dto.PaymentResponse;
import store.carjava.marketplace.app.payment.exception.PaymentBodyNullException;
import store.carjava.marketplace.app.payment.exception.PaymentClientErrorException;
import store.carjava.marketplace.app.payment.exception.PaymentRequestUserMismatchException;
import store.carjava.marketplace.app.user.entity.User;
import store.carjava.marketplace.app.user.exception.UserIdNotFoundException;
import store.carjava.marketplace.app.user.repository.UserRepository;
import store.carjava.marketplace.common.util.user.UserResolver;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private static final String TOSS_API_URL = "https://api.tosspayments.com/v1/payments/confirm";
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ISO_OFFSET_DATE_TIME;

    private final CarPurchaseHistoryRepository carPurchaseHistoryRepository;
    private final MarketplaceCarRepository marketplaceCarRepository;
    private final UserResolver userResolver;
    private final UserRepository userRepository;

    @Value("${payment.secret}")
    private String widgetSecretKey;

    public PaymentResponse processPaymentConfirmation(PaymentRequest paymentRequest) {
        try {
            // API 호출
            ResponseEntity<Map> tossPaymentApiResponse = callTossPaymentApi(paymentRequest);

            // 응답 처리
            Map<String, Object> apiResponse = extractApiResponse(tossPaymentApiResponse);

            // 구매 내역 저장
            saveCarPurchaseHistory(apiResponse);

            // PaymentResponse 생성
            return createPaymentResponse(apiResponse);
        } catch (HttpClientErrorException e) {
            throw new PaymentClientErrorException();
        }
    }

    private ResponseEntity<Map> callTossPaymentApi(PaymentRequest request) {
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<Map<String, String>> requestEntity = new HttpEntity<>(makeRequestBody(request), makeRequestHeaders());
        return restTemplate.postForEntity(TOSS_API_URL, requestEntity, Map.class);
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

    private String createAuthorizationHeader() {
        return "Basic " + Base64.getEncoder().encodeToString((widgetSecretKey + ":").getBytes(StandardCharsets.UTF_8));
    }

    private Map<String, Object> extractApiResponse(ResponseEntity<Map> apiResponseEntity) {
        Map<String, Object> apiResponse = apiResponseEntity.getBody();
        if (apiResponse == null) {
            throw new PaymentBodyNullException();
        }
        return apiResponse;
    }

    private Map<String, Object> extractEasyPay(Map<String, Object> apiResponse) {
        return (Map<String, Object>) apiResponse.get("easyPay");
    }

    private Map<String, String> extractMetadata(Map<String, Object> apiResponse) {
        return (Map<String, String>) apiResponse.get("metadata");
    }

    private LocalDateTime parseDate(String date) {
        return OffsetDateTime.parse(date, FORMATTER).toLocalDateTime();
    }

    private void saveCarPurchaseHistory(Map<String, Object> apiResponse) {
//        User currentUser = userResolver.getCurrentUser();
        User currentUser = userRepository.findById(3L)
                .orElseThrow(UserIdNotFoundException::new);

        validateRequestUserSameAsCurrentUser(currentUser, apiResponse);

        Map<String, Object> easyPay = extractEasyPay(apiResponse);
        Map<String, String> metadata = extractMetadata(apiResponse);

        MarketplaceCar marketplaceCar = marketplaceCarRepository.findById(metadata.get("marketplaceCarId"))
                .orElseThrow(MarketplaceCarIdNotFoundException::new);

        // MarketplaceCar status 업데이트
        marketplaceCar.updateStatus("PENDING_PURCHASE_APPROVAL");
        marketplaceCarRepository.save(marketplaceCar);

        CarPurchaseHistory carPurchaseHistory = CarPurchaseHistory.builder()
                .orderId((String) apiResponse.get("orderId"))
                .orderName((String) apiResponse.get("orderName"))
                .approvedAt(parseDate((String) apiResponse.get("approvedAt")))
                .confirmedAt(null)
                .currency((String) apiResponse.get("currency"))
                .totalAmount(((Number) apiResponse.get("totalAmount")).longValue())
                .suppliedAmount(((Number) apiResponse.get("suppliedAmount")).longValue())
                .vat(((Number) apiResponse.get("vat")).longValue())
                .paymentMethod(easyPay != null ? (String) easyPay.get("provider") : null)
                .marketplaceCar(marketplaceCar)
                .user(currentUser)
                .build();

        carPurchaseHistoryRepository.save(carPurchaseHistory);
    }

    private void validateRequestUserSameAsCurrentUser(User currentUser, Map<String, Object> apiResponse) {
        Map<String, String> metadata = extractMetadata(apiResponse);
        Long requestUserId = Long.valueOf(metadata.get("userId"));
        if (!Objects.equals(currentUser.getId(), requestUserId)) {
            throw new PaymentRequestUserMismatchException();
        }
    }

    private PaymentResponse createPaymentResponse(Map<String, Object> apiResponse) {
        Map<String, Object> easyPay = extractEasyPay(apiResponse);
        return new PaymentResponse(
                (String) apiResponse.get("orderId"),
                (String) apiResponse.get("orderName"),
                parseDate((String) apiResponse.get("requestedAt")),
                parseDate((String) apiResponse.get("approvedAt")),
                (String) apiResponse.get("currency"),
                ((Number) apiResponse.get("totalAmount")).longValue(),
                ((Number) apiResponse.get("suppliedAmount")).longValue(),
                ((Number) apiResponse.get("vat")).longValue(),
                easyPay != null ? (String) easyPay.get("provider") : null
        );
    }
}