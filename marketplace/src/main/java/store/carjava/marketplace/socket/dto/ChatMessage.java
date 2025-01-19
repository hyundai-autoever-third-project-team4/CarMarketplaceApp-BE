package store.carjava.marketplace.socket.dto;


public record ChatMessage(
        Long senderId,  // 채팅을 보내는 client ID
        String content  // 채팅 내용
) {
}
