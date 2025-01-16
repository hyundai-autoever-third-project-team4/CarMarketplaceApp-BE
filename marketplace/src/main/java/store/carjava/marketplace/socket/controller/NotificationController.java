package store.carjava.marketplace.socket.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import store.carjava.marketplace.socket.dto.ChatMessage;

@RestController
public class NotificationController {

    private final SimpMessagingTemplate template;

    public NotificationController(SimpMessagingTemplate template, WebSocketController webSocketController) {
        this.template = template;
    }

    @PostMapping("/api/notify")
    public void notifyClients(@RequestBody ChatMessage message) {
        // 클라이언트가 구독 중인 "/topic/greetings" 경로로 메시지 발송
        template.convertAndSend("/topic/greetings", message);
    }

}
