package store.carjava.marketplace.firebase.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import store.carjava.marketplace.app.user.entity.User;
import store.carjava.marketplace.app.user.exception.UserIdNotFoundException;
import store.carjava.marketplace.app.user.repository.UserRepository;
import store.carjava.marketplace.firebase.dto.GetTokenRequest;
import store.carjava.marketplace.firebase.entity.FCMToken;
import store.carjava.marketplace.firebase.repository.FCMTokenRepository;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class FirebaseService {
    private final FCMTokenRepository fcmTokenRepository;
    private final UserRepository userRepository;

    public void getToken(GetTokenRequest fcmtoken) {
        // 1) 저장된 토큰인지 체크
        Optional<FCMToken> userFCMToken = fcmTokenRepository.findByUserIdAndFcmToken(fcmtoken.userId(), fcmtoken.token());

        // 2-1) 있는 경우 토큰 시간 갱신
        FCMToken fcmToken;
        if (userFCMToken.isPresent()) {
            fcmToken = userFCMToken.get().updateFCMToken(fcmtoken.currentTime());
        }
        // 2-2) 없는 경우 저장
        else{
            User user = userRepository.findById(fcmtoken.userId())
                    .orElseThrow(UserIdNotFoundException::new);
            fcmToken = FCMToken.builder()
                    .user(user)
                    .fcmToken(fcmtoken.token())
                    .createdAt(fcmtoken.currentTime())
                    .updatedAt(fcmtoken.currentTime())
                    .build();
        }

        fcmTokenRepository.save(fcmToken);
    }

    public void deleteToken(String token) {
        fcmTokenRepository.deleteByFcmToken(token);
    }
}
