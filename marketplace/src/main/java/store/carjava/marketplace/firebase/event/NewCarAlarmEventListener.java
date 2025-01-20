package store.carjava.marketplace.firebase.event;

import com.google.firebase.messaging.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import store.carjava.marketplace.app.like.entity.Like;
import store.carjava.marketplace.app.like.repository.LikeRepository;
import store.carjava.marketplace.app.marketplace_car.event.CarSellEvent;
import store.carjava.marketplace.firebase.repository.FCMTokenRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Slf4j
@Component
@RequiredArgsConstructor
public class NewCarAlarmEventListener {
    private final FCMTokenRepository fcmTokenRepository;
    private final LikeRepository likeRepository;

    @EventListener
    @Transactional
    public void handleNewCarAlarmEvent(CarSellEvent event){
        //입고된 차랑 동일한 모델을 찜한 사용자를 가져온다.
        Set<Long> userIdList = likeRepository.findUserByMarketplaceCar_CarDetails_Model(event.getModel());

        //사용자들 기기에 알림 요청을 한다.
        List<String> tokens;
        if(!userIdList.isEmpty()) {
            tokens = fcmTokenRepository.findAllByUserIdIn(userIdList);

            if(!tokens.isEmpty()) {
                // 토큰들에 알람 요청
                MulticastMessage message = MulticastMessage.builder()
                        .setNotification(Notification.builder()
                                .setTitle("새 중고차 입고")
                                .setBody("내가 찜한 차와 같은 모델이 입고 됐습니다")
                                .build())
                        .putData("url", "https://chajava.store/carDetail/HUS241223011180")
                        .addAllTokens(tokens)
                        .build();

                // 만료된 요청이 오는 토큰 삭제
                try{
                    BatchResponse response = FirebaseMessaging.getInstance().sendEachForMulticast(message);
                    if (response.getFailureCount() > 0 ){
                        List<SendResponse> responses = response.getResponses();
                        for (int i = 0; i < responses.size(); i++) {
                            if (!responses.get(i).isSuccessful()){
                                // 만료 토큰 삭제
                                fcmTokenRepository.deleteByFcmToken(tokens.get(i));
                            }
                        }
                    }
                    log.info("알람 전송 완료");
                }catch (Exception e){
                    log.info("알람 전송 실패 : " + e.getMessage());
                }

            }



        }
    }
}
