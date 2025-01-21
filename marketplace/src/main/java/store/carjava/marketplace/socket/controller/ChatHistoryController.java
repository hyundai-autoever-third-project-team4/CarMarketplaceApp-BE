package store.carjava.marketplace.socket.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import store.carjava.marketplace.socket.dto.ChatHistoryDto;
import store.carjava.marketplace.socket.dto.ChatHistoryResponse;
import store.carjava.marketplace.socket.service.ChatHistoryService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ChatHistoryController {
    private final ChatHistoryService chatHistoryService;

    @GetMapping("/chat/histories")
    public ResponseEntity<?> getAllChatHistoriesByTopicId() {
        List<ChatHistoryDto> chatHistoryDtos = chatHistoryService.getAllChatHistories();

        ChatHistoryResponse response = ChatHistoryResponse.of(chatHistoryDtos);

        return ResponseEntity.ok(response);
    }
}
