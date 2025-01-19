package store.carjava.marketplace.socket.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import store.carjava.marketplace.socket.dto.ChatMessage;

@Component
@RequiredArgsConstructor
@Slf4j
public class ChatMessageListener {
    private final SimpMessagingTemplate messagingTemplate;

    @RabbitListener(queues = "chat.queue")
    public void handleMessage(ChatMessage message) {
        log.info("Listened : {}", message);

        // WebSocket을 통해 메시지를 전송
        messagingTemplate.convertAndSend("/queue/" + message.senderId(), message);
    }
}
