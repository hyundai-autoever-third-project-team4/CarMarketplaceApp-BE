package store.carjava.marketplace.web.admin.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import store.carjava.marketplace.app.car_purchase_history.entity.CarPurchaseHistory;
import store.carjava.marketplace.app.car_purchase_history.repository.CarPurchaseHistoryRepository;
import store.carjava.marketplace.app.marketplace_car.entity.MarketplaceCar;
import store.carjava.marketplace.app.marketplace_car.repository.MarketplaceCarRepository;
import store.carjava.marketplace.app.user.entity.User;
import store.carjava.marketplace.app.user.repository.UserRepository;
import store.carjava.marketplace.web.admin.dto.CarPurchaseDto;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final UserRepository userRepository;
    private final CarPurchaseHistoryRepository carPurchaseHistoryRepository;
    private final MarketplaceCarRepository marketplaceCarRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Long getTotalSalesAmount() {
        return carPurchaseHistoryRepository.findTotalAmountSum();
    }


    // 구매 내역에서 차량 상태가 대기중인 차 조회.
    public List<CarPurchaseDto> getPendingPurchaseHistory() {
        List<CarPurchaseHistory> purchaseHistories = carPurchaseHistoryRepository.findAllByMarketplaceCarStatus("PENDING_PURCHASE_APPROVAL");
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
        List<CarPurchaseHistory> purchaseHistories = carPurchaseHistoryRepository.findAllByMarketplaceCarStatus("NOT_AVAILABLE_FOR_PURCHASE");
        return purchaseHistories.stream()
                .map(CarPurchaseDto::of)
                .collect(Collectors.toList());
    }
}
