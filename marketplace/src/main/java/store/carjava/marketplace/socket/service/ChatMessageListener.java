package store.carjava.marketplace.socket.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import store.carjava.marketplace.socket.dto.ChatMessageDto;

@Component
@RequiredArgsConstructor
@Slf4j
public class ChatMessageListener {
    private final SimpMessagingTemplate messagingTemplate;

    @RabbitListener(queues = "chat.queue")
    public void handleMessage(ChatMessageDto message) {
        log.info("Rabbit MQ 메세징 큐에서 받은 메세지 : {}", message);

        if (message.isAdmin()) {
            log.info("[ADMIN] ⇒ [USER # {}]", message.receiverId());
            messagingTemplate.convertAndSend("/queue/user-" + message.senderId(), message);
        } else {
            log.info("[USER] ⇒ [ADMIN]");
            messagingTemplate.convertAndSend("/topic/admin", message);
        }
    }
}
