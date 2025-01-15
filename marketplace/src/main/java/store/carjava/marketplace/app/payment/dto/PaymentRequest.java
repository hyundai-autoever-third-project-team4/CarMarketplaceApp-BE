package store.carjava.marketplace.app.payment.dto;

public record PaymentRequest(
        String paymentKey,
        String orderId,
        String amount
) {

}