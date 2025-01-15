package store.carjava.marketplace.app.payment.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import store.carjava.marketplace.app.payment.dto.PaymentRequest;
import store.carjava.marketplace.app.payment.dto.PaymentResponse;
import store.carjava.marketplace.app.payment.service.PaymentService;

import java.util.Map;

@RestController
@Slf4j
@Tag(name = "결제 API", description = "차량 결제 관련 API를 제공합니다.")
@RequestMapping("/payment")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @Operation(summary = "차량 구매 승인", description = "차량 결제 금액 처리 엔드포인트")
    @PostMapping("/confirm")
    public ResponseEntity<?> confirmPayment(@RequestBody PaymentRequest request) {
        PaymentResponse paymentResponse = paymentService.processPaymentConfirmation(request);

        return ResponseEntity.ok(paymentResponse);
    }
}