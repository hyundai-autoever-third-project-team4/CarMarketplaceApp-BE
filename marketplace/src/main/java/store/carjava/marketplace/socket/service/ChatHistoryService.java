package store.carjava.marketplace.socket.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import store.carjava.marketplace.app.user.entity.User;
import store.carjava.marketplace.common.util.user.UserResolver;
import store.carjava.marketplace.socket.dto.ChatHistoryDto;
import store.carjava.marketplace.socket.dto.ChatHistoryResponse;
import store.carjava.marketplace.socket.entity.ChatHistory;
import store.carjava.marketplace.socket.repository.ChatHistoryRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatHistoryService {
    private final ChatHistoryRepository chatHistoryRepository;
    private final UserResolver userResolver;

    public ChatHistoryResponse getAllChattings() {
        User currentUser = userResolver.getCurrentUser();

        List<ChatHistory> chatHistories = chatHistoryRepository.findAllBySenderId(currentUser.getId());

        List<ChatHistoryDto> chatHistoryDtos = chatHistories.stream()
                .map(ChatHistoryDto::from)
                .toList();

        return ChatHistoryResponse.of(chatHistoryDtos);
    }

}
