package store.carjava.marketplace.socket.dto;

import java.util.List;

public record ChatHistoryResponse(
        List<ChatHistoryDto> chatHistoryDtos
) {
    public static ChatHistoryResponse of(List<ChatHistoryDto> chatHistoryDtos) {
        return new ChatHistoryResponse(chatHistoryDtos);
    }
}
