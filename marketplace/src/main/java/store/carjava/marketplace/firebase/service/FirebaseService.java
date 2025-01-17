package store.carjava.marketplace.firebase.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import store.carjava.marketplace.firebase.dto.GetTokenRequest;
import store.carjava.marketplace.firebase.repository.FCMTokenRepository;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class FirebaseService {
    private final FCMTokenRepository fcmTokenRepository;

    public void getToken(GetTokenRequest fcmtoken) {
        log.info("token: {}", fcmtoken.token());
        log.info("user: {}", fcmtoken.userId());

        return;
    }
}
