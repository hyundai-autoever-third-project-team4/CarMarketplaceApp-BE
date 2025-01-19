package store.carjava.marketplace.socket.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import store.carjava.marketplace.socket.dto.ChatMessage;

@Controller
@RequiredArgsConstructor
@Slf4j
public class ChatController {

    private final RabbitTemplate rabbitTemplate;

    @MessageMapping("/chat.send")
    public void sendMessage(ChatMessage message) {
        // 로그로 메시지 내용 출력
        log.info("Received message: {}", message);

        // Rabbit MQ에 메세지 전송
        rabbitTemplate.convertAndSend("chat.queue", message);
    }
}
