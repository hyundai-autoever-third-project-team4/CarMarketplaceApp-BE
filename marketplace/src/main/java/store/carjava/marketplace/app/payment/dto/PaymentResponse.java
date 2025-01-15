package store.carjava.marketplace.app.payment.dto;

import java.time.LocalDateTime;

public record PaymentResponse(
        String orderId, // 주문 id
        String orderName,   // 주문한 아이템 이름
        LocalDateTime requestedAt,  // 주문 요청 일자
        LocalDateTime approvedAt,   // 주문 확정 일자
        String currency,    // 원화 (KRW)
        Long totalAmount,   // 전체결제금액
        Long suppliedAmount,    // 세금 공제 금액
        Long vat,   // 세금 금액
        String paymentMethod    // 결제 방식
) {

}
