package store.carjava.marketplace.socket.controller;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import store.carjava.marketplace.socket.dto.ChatMessage;

@Controller
@RequiredArgsConstructor
public class WebSocketController {

    private final SimpMessagingTemplate messagingTemplate;

    // 사용자 → 관리자 메시지 전송
    @MessageMapping("/userToAdmin")
    @SendTo("/topic/admin") // 관리자만 구독
    public ChatMessage userToAdmin(ChatMessage message) {
        return new ChatMessage(message.sender(), message.content(), "USER_MESSAGE");
    }

    // 관리자 → 사용자 메시지 전송
    @MessageMapping("/adminToUser")
    @SendTo("/topic/user") // 사용자만 구독
    public ChatMessage adminToUser(ChatMessage message) {
        return new ChatMessage(message.sender(), message.content(), "ADMIN_MESSAGE");
    }


}


