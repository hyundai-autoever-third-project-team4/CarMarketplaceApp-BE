package store.carjava.marketplace.firebase.scheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import store.carjava.marketplace.firebase.repository.FCMTokenRepository;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Slf4j
@Component
@RequiredArgsConstructor
public class FCMDeleteSchedule {
    private final FCMTokenRepository fcmTokenRepository;

    @Scheduled(cron = "0 0 0 * * *")
    public void deleteFCM() {
        LocalDateTime now = LocalDateTime.now(ZoneId.of("UTC")).minusMonths(2);
        fcmTokenRepository.deleteOldTokens(now);
        log.info("Deleting old FCM");
    }
}
