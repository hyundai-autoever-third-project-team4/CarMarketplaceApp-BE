package store.carjava.marketplace.app.payment.dto;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

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
    public static PaymentResponse from(Map<String, Object> response) {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME;

        return new PaymentResponse(
                (String) response.get("orderId"),
                (String) response.get("orderName"),
                OffsetDateTime.parse((String) response.get("requestedAt"), formatter).toLocalDateTime(),
                OffsetDateTime.parse((String) response.get("approvedAt"), formatter).toLocalDateTime(),
                (String) response.get("currency"),
                ((Number) response.get("totalAmount")).longValue(),
                ((Number) response.get("suppliedAmount")).longValue(),
                ((Number) response.get("vat")).longValue(),
                (String) response.get("method")
        );
    }
}

