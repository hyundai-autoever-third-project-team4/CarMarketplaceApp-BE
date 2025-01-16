package store.carjava.marketplace.socket.dto;


public record ChatMessage(
        String sender,
        String content,
        String type
) {
}
