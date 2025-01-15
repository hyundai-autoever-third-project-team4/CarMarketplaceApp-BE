package store.carjava.marketplace.app.payment.controller;

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
@RequestMapping("/payment")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/confirm")
    public ResponseEntity<?> confirmPayment(@RequestBody PaymentRequest request) {
        PaymentResponse paymentResponse = paymentService.processPaymentConfirmation(request);

        return ResponseEntity.ok(paymentResponse);
    }
}