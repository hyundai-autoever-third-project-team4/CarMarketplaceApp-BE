package store.carjava.marketplace.socket.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import store.carjava.marketplace.socket.dto.ChatMessage;

@Controller
@Slf4j
public class ChatController {

    private final SimpMessagingTemplate messagingTemplate;

    public ChatController(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @MessageMapping("/chat.send")
    public void sendMessage(ChatMessage message) {
        // 로그로 메시지 내용 출력
        log.info("Received message: {}", message);

        // 특정 사용자 채팅방으로 메시지 전송
        messagingTemplate.convertAndSend("/queue/" + message.senderId(), message);
    }
}
