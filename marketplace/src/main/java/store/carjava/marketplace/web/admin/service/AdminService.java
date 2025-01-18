package store.carjava.marketplace.web.admin.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import store.carjava.marketplace.app.car_purchase_history.entity.CarPurchaseHistory;
import store.carjava.marketplace.app.car_purchase_history.repository.CarPurchaseHistoryRepository;
import store.carjava.marketplace.app.car_sales_history.dto.CarSalesHistoryInfoDto;
import store.carjava.marketplace.app.car_sales_history.entity.CarSalesHistory;
import store.carjava.marketplace.app.car_sales_history.repository.CarSalesHistoryRepository;
import store.carjava.marketplace.app.marketplace_car.dto.MarketplaceCarSummaryDto;
import store.carjava.marketplace.app.marketplace_car.entity.MarketplaceCar;
import store.carjava.marketplace.app.marketplace_car.repository.MarketplaceCarRepository;
import store.carjava.marketplace.app.reservation.dto.ReservationDetailDto;
import store.carjava.marketplace.app.reservation.entity.Reservation;
import store.carjava.marketplace.app.reservation.repository.ReservationRepository;
import store.carjava.marketplace.app.review.repository.ReviewRepository;
import store.carjava.marketplace.app.user.dto.UserSummaryDto;
import store.carjava.marketplace.app.user.entity.User;
import store.carjava.marketplace.app.user.repository.UserRepository;
import store.carjava.marketplace.web.admin.dto.CarPurchaseDto;
import store.carjava.marketplace.web.admin.dto.CarSellDto;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final UserRepository userRepository;
    private final CarPurchaseHistoryRepository carPurchaseHistoryRepository;
    private final MarketplaceCarRepository marketplaceCarRepository;
    private final CarSalesHistoryRepository carSalesHistoryRepository;
    private final ReviewRepository reviewRepository;
    private final ReservationRepository reservationRepository;

    // 전체 사용자 목록을 페이지네이션으로 가져오는 메서드
    public Page<UserSummaryDto> getUsers(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return userRepository.findAll(pageable).map(UserSummaryDto::of);
    }

    // 이메일로 사용자 검색 (페이지네이션 포함)
    public Page<UserSummaryDto> searchUsersByEmail(String email, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return userRepository.findByEmailContaining(email, pageable).map(UserSummaryDto::of);
    }

    public Long getTotalSalesAmount() {
        return carPurchaseHistoryRepository.findTotalAmountSum();
    }

    public Long getTotalReviews() {
        return reviewRepository.count();
    }

    public Long getTotalSales() {
        return carSalesHistoryRepository.count();
    }

    public Long getTotalPurchases() {
        return carPurchaseHistoryRepository.count();
    }

    public List<CarPurchaseDto> getCarPurchases() {
        return carPurchaseHistoryRepository.findAllByMarketplaceCarStatus(
                        "PENDING_PURCHASE_APPROVAL")
                .stream().map(CarPurchaseDto::of)
                .collect(Collectors.toList());
    }

    public List<CarSellDto> getCarSales() {
        return carSalesHistoryRepository.findAllByMarketplaceCarStatus("PENDING_SALE")
                .stream().map(CarSellDto::of)
                .collect(Collectors.toList());
    }

    // 구매 내역에서 차량 상태가 대기중인 차 조회.
    public List<CarPurchaseDto> getPendingPurchaseHistory() {
        List<CarPurchaseHistory> purchaseHistories = carPurchaseHistoryRepository.findAllByMarketplaceCarStatus(
                "PENDING_PURCHASE_APPROVAL");
        return purchaseHistories.stream()
                .map(CarPurchaseDto::of)
                .collect(Collectors.toList());
    }

    // 관리자의 구매 승인
    @Transactional
    public void approvePurchase(Long id) {
        CarPurchaseHistory purchase = carPurchaseHistoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid purchase ID"));

        MarketplaceCar car = purchase.getMarketplaceCar();
        car.updateStatus("NOT_AVAILABLE_FOR_PURCHASE");
        purchase.confirm();
    }

    // 구매 내역에서 구매가 완료된 차량 조회.
    public List<CarPurchaseDto> getCompletedPurchaseHistory() {
        List<CarPurchaseHistory> purchaseHistories = carPurchaseHistoryRepository.findAllByMarketplaceCarStatus(
                "NOT_AVAILABLE_FOR_PURCHASE");
        return purchaseHistories.stream()
                .map(CarPurchaseDto::of)
                .collect(Collectors.toList());
    }

    // 판매 내역에서 차량 상태가 승인 대기중인 차량 조회.
    public List<CarSellDto> getPendingSaleHistory() {
        List<CarSalesHistory> salesHistories = carSalesHistoryRepository.findAllByMarketplaceCarStatus(
                "PENDING_SALE");
        return salesHistories.stream()
                .map(CarSellDto::of)
                .collect(Collectors.toList());
    }

    // 판매 내역에서 차량 상태가 승인 완료된 차량 조회
    public List<CarSellDto> getApprovedSaleHistory() {
        List<CarSalesHistory> salesHistories = carSalesHistoryRepository.findAllByMarketplaceCarStatus(
                "SALE_APPROVED");
        return salesHistories.stream()
                .map(CarSellDto::of)
                .collect(Collectors.toList());
    }

    public Page<MarketplaceCarSummaryDto> getCars(int page, int size) {
        return marketplaceCarRepository.findAll(PageRequest.of(page, size))
                .map(MarketplaceCarSummaryDto::of);
    }

    public Page<MarketplaceCarSummaryDto> searchCarsByLicensePlate(String licensePlate, int page,
            int size) {
        return marketplaceCarRepository.findByCarDetailsLicensePlate(licensePlate,
                        PageRequest.of(page, size))
                .map(MarketplaceCarSummaryDto::of);
    }

    public Page<ReservationDetailDto> getReservationDetails(String reservationName,
            String licensePlate, String status, String reservationDate, Pageable pageable) {
        Page<Reservation> reservationsPage = reservationRepository.findAllWithFilters(
                reservationName, licensePlate, status, reservationDate, pageable);
        Page<ReservationDetailDto> dtoPage = reservationsPage.map(ReservationDetailDto::fromEntity);
        return dtoPage;
    }
}
