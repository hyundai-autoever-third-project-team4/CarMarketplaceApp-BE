package store.carjava.marketplace.app.reservation.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import store.carjava.marketplace.app.reservation.dto.ReservationCreateRequest;
import store.carjava.marketplace.app.reservation.dto.ReservationCreateResponse;
import store.carjava.marketplace.app.reservation.dto.ReservationListResponse;
import store.carjava.marketplace.app.reservation.service.ReservationService;

@RestController
@RequiredArgsConstructor
@Tag(name = "예약 API", description = "예약 관련 API를 제공합니다.")
public class ReservationController {

    private final ReservationService reservationService;

    @Operation(summary = "예약 생성", description = "새로운 예약을 생성합니다.")
    @PostMapping("/reservation")
    public ResponseEntity<ReservationCreateResponse> createReservation(
            @RequestBody ReservationCreateRequest request) {
        ReservationCreateResponse response = reservationService.createReservation(request);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "차량별 예약 조회", description = "특정 차량의 모든 예약 정보를 조회합니다.")
    @GetMapping("/reservations/marketplace-cars/{marketplaceCarId}")
    public ResponseEntity<ReservationListResponse> getMarketplaceCarReservations(
            @Parameter(description = "예약을 조회할 대상 차량 ID", required = true, example = "123")
            @PathVariable("marketplaceCarId") String marketplaceCarId) {
        ReservationListResponse response = reservationService.getMarketplaceCarReservations(marketplaceCarId);
        return ResponseEntity.ok(response);
    }
}
