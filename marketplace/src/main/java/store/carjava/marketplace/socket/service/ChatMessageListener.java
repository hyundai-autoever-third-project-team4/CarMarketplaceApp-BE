package store.carjava.marketplace.socket.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import store.carjava.marketplace.socket.dto.ChatMessageDto;
import store.carjava.marketplace.socket.entity.ChatHistory;
import store.carjava.marketplace.socket.repository.ChatHistoryRepository;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
@Slf4j
public class ChatMessageListener {
    private final SimpMessagingTemplate messagingTemplate;
    private final ChatHistoryRepository chatHistoryRepository;

    @RabbitListener(queues = "chat.queue")
    public void handleMessage(ChatMessageDto message) {
        log.info("Rabbit MQ 메세징 큐에서 받은 메세지 : {}", message);

        log.info("[ADMIN] ⇒ [USER # {}]", message.receiverId());

        Long topicId = message.senderId();  // topic id를 전송자로 설정

        if (message.isAdmin()) {
            // admin인 경우에는, topic을 receiver로 설정
            topicId = message.receiverId();
        }

        ChatHistory chatHistory = ChatHistory.builder()
                .senderId(message.senderId())
                .receiverId(message.receiverId())
                .content(message.content())
                .createdAt(LocalDateTime.now())
                .topicId(topicId)
                .build();

        chatHistoryRepository.save(chatHistory);

        messagingTemplate.convertAndSend("/queue/user-" + message.receiverId(), message);

    }
}
