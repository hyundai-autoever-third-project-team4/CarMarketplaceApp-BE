package store.carjava.marketplace.socket.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import store.carjava.marketplace.socket.dto.ChatHistoryResponse;
import store.carjava.marketplace.socket.service.ChatHistoryService;

@RestController
@RequiredArgsConstructor
public class ChatHistoryController {
    private final ChatHistoryService chatHistoryService;

    @GetMapping("/chatList")
    public ResponseEntity<?> getAllChattings() {
        ChatHistoryResponse response = chatHistoryService.getAllChattings();

        return ResponseEntity.ok(response);
    }
}
