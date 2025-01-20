package store.carjava.marketplace.socket.dto;

public record ChatMessageDto(
        Long senderId,
        Long receiverId,
        String content,
        boolean isAdmin
) {
    public static ChatMessageDto of(ChatMessageRequest chatMessageRequest, boolean isAdmin) {
        return new ChatMessageDto(
                chatMessageRequest.senderId(),
                chatMessageRequest.receiverId(),
                chatMessageRequest.content(),
                isAdmin
        );
    }
}
