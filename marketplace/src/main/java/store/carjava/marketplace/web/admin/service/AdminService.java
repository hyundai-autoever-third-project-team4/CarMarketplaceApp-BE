package store.carjava.marketplace.web.admin.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import store.carjava.marketplace.app.car_purchase_history.repository.CarPurchaseHistoryRepository;
import store.carjava.marketplace.app.user.entity.User;
import store.carjava.marketplace.app.user.repository.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final UserRepository userRepository;
    private final CarPurchaseHistoryRepository carPurchaseHistoryRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Long getTotalSalesAmount() {
        return carPurchaseHistoryRepository.findTotalAmountSum();
    }

}
