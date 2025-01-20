package store.carjava.marketplace.socket.dto;

import store.carjava.marketplace.socket.entity.ChatHistory;

import java.time.LocalDateTime;

public record ChatHistoryDto(
        Long senderId,
        Long receiverId,
        String content,
        Long topicId,
        LocalDateTime createdAt
) {
    public static ChatHistoryDto from(ChatHistory chatHistory) {
        return new ChatHistoryDto(
                chatHistory.getSenderId(),
                chatHistory.getReceiverId(),
                chatHistory.getContent(),
                chatHistory.getTopicId(),
                chatHistory.getCreatedAt()
        );
    }
}
