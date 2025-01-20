package store.carjava.marketplace.socket.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import store.carjava.marketplace.app.user.entity.User;
import store.carjava.marketplace.app.user.exception.UserIdNotFoundException;
import store.carjava.marketplace.app.user.repository.UserRepository;
import store.carjava.marketplace.common.util.user.UserResolver;
import store.carjava.marketplace.socket.dto.ChatMessageRequest;
import store.carjava.marketplace.socket.dto.ChatMessageDto;

@Controller
@RequiredArgsConstructor
@Slf4j
public class ChatController {

    private final RabbitTemplate rabbitTemplate;
    private final UserRepository userRepository;

    @MessageMapping("/chat.send")
    public void sendMessage(ChatMessageRequest messageRequest) {
        User sender = userRepository.findById(messageRequest.senderId())
                .orElseThrow(UserIdNotFoundException::new);

        boolean isAdmin = false;

        if (sender.getRole().equals("ROLE_ADMIN")) {
            isAdmin = true;
        }

        log.info("메세지 송신자의 ROLE : {}", isAdmin ? "ADMIN" : "USER");

        ChatMessageDto chatMessageDto = ChatMessageDto.of(messageRequest, isAdmin);

        // 로그로 메시지 내용 출력
        log.info("서버가 받은 메세지 : {}", chatMessageDto);

        // Rabbit MQ에 메세지 전송
        rabbitTemplate.convertAndSend("chat.queue", chatMessageDto);
    }
}
