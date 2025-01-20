package store.carjava.marketplace.web.admin.service;

import jakarta.transaction.Transactional;

import java.io.IOException;
import java.time.LocalDate;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import store.carjava.marketplace.app.car_purchase_history.entity.CarPurchaseHistory;
import store.carjava.marketplace.app.car_purchase_history.repository.CarPurchaseHistoryRepository;
import store.carjava.marketplace.app.car_sales_history.dto.CarSalesHistoryInfoDto;
import store.carjava.marketplace.app.car_sales_history.entity.CarSalesHistory;
import store.carjava.marketplace.app.car_sales_history.repository.CarSalesHistoryRepository;
import store.carjava.marketplace.app.marketplace_car.dto.MarketplaceCarSummaryDto;
import store.carjava.marketplace.app.marketplace_car.entity.MarketplaceCar;
import store.carjava.marketplace.app.marketplace_car.exception.MarketplaceCarIdNotFoundException;
import store.carjava.marketplace.app.marketplace_car.repository.MarketplaceCarRepository;
import store.carjava.marketplace.app.marketplace_car_image.service.MarketplaceCarImageService;
import store.carjava.marketplace.app.reservation.dto.ReservationDetailDto;
import store.carjava.marketplace.app.reservation.entity.Reservation;
import store.carjava.marketplace.app.reservation.repository.ReservationRepository;
import store.carjava.marketplace.app.review.repository.ReviewRepository;
import store.carjava.marketplace.app.test_drive_center.entity.TestDriveCenter;
import store.carjava.marketplace.app.test_drive_center.exception.TestDriverCenterIdNotFoundException;
import store.carjava.marketplace.app.test_drive_center.repository.TestDriveCenterRepository;
import store.carjava.marketplace.app.user.dto.UserSummaryDto;
import store.carjava.marketplace.app.user.entity.User;
import store.carjava.marketplace.app.user.exception.UserIdNotFoundException;
import store.carjava.marketplace.app.user.repository.UserRepository;
import store.carjava.marketplace.common.util.image.ImageUploader;
import store.carjava.marketplace.socket.repository.ChatHistoryRepository;
import store.carjava.marketplace.web.admin.dto.CarPurchaseDto;
import store.carjava.marketplace.web.admin.dto.CarSellDto;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
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
    private final TestDriveCenterRepository testDriveCenterRepository;
    private final ImageUploader imageUploader;
    private final MarketplaceCarImageService marketplaceCarImageService;
    private final ChatHistoryRepository chatHistoryRepository;

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
        return marketplaceCarRepository.findByLicensePlateStartingWith(licensePlate,
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

    public Page<MarketplaceCarSummaryDto> searchCarsByModel(String model, int page, int size) {
        return marketplaceCarRepository.findByCarDetailsModel(model, PageRequest.of(page, size))
                .map(MarketplaceCarSummaryDto::of);
    }

    public List<String> getDistinctCarModels() {
        return marketplaceCarRepository.findDistinctModels();
    }

    public Page<MarketplaceCarSummaryDto> searchCarsByStatus(String status, int page, int size) {
        return marketplaceCarRepository.findByStatus(status, PageRequest.of(page, size))
                .map(MarketplaceCarSummaryDto::of);
    }

    // 관리자가 판매 승인했을 때 가격
    @Transactional
    public void approveCar(String id, Long testDriveCenterId, Long price, List<MultipartFile> files)
            throws IOException {
        // 차량 조회
        MarketplaceCar car = marketplaceCarRepository.findById(id)
                .orElseThrow(MarketplaceCarIdNotFoundException::new);

        // TestDriveCenter 조회
        TestDriveCenter testDriveCenter = testDriveCenterRepository.findById(testDriveCenterId)
                .orElseThrow(TestDriverCenterIdNotFoundException::new);

        // 차량 상태 업데이트 및 TestDriveCenter 연관 관계 설정
        car = MarketplaceCar.builder()
                .id(car.getId())
                .carDetails(car.getCarDetails())
                .price(price)
                .status("SALE_APPROVED")
                .marketplaceRegistrationDate(LocalDate.now())
                .testDriveCenter(testDriveCenter) // 연관된 TestDriveCenter 설정
                .mainImage(car.getMainImage())
                .carSalesHistories(car.getCarSalesHistories())
                .build();

        // 차량 저장
        marketplaceCarRepository.save(car);

        // 이미지 파일 처리 및 저장
        if (files != null && !files.isEmpty()) {
            // S3에 파일 업로드 및 URL 생성
            List<String> imageUrls = imageUploader.uploadMultiFiles(files,
                    "marketplace-car-images");

            // 서비스 호출로 DB 저장
            marketplaceCarImageService.saveImages(id, imageUrls);
        }
    }

    public Map<Long, String> getAllChattingRooms() {
        List<Long> topicIds = chatHistoryRepository.findDistinctTopicIds();

        // 채팅방 이름은 topicId를 기반으로 생성
        Map<Long, String> chatRooms = new LinkedHashMap<>();
        for (Long topicId : topicIds) {
            User user = userRepository.findById(topicId).orElseThrow(UserIdNotFoundException::new);

            chatRooms.put(topicId, "USER : [" + user.getName() + "]");
        }

        return chatRooms;
    }

    public User getUserByTopicId(Long topicId) {
        return userRepository.findById(topicId)
                .orElseThrow(UserIdNotFoundException::new);
    }
}
