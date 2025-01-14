package store.carjava.marketplace.app.reservation.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import store.carjava.marketplace.app.reservation.dto.ReservationCreateRequest;
import store.carjava.marketplace.app.reservation.dto.ReservationCreateResponse;
import store.carjava.marketplace.app.reservation.dto.ReservationListResponse;
import store.carjava.marketplace.app.reservation.service.ReservationService;

@RestController
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    @PostMapping("/reservation")
    public ResponseEntity<ReservationCreateResponse> createReservation(@RequestBody ReservationCreateRequest request) {
        ReservationCreateResponse response = reservationService.createReservation(request);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/reservations/marketplace-cars/{marketplaceCarId}")
    public ResponseEntity<?> getMarketplaceCarReservations(@PathVariable("marketplaceCarId") String marketplaceCarId) {
        ReservationListResponse response = reservationService.getMarketplaceCarReservations(marketplaceCarId);

        return ResponseEntity.ok(response);
    }
}
