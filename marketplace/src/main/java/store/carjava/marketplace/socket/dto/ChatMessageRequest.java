package store.carjava.marketplace.socket.dto;


public record ChatMessageRequest(
        Long senderId,  // 채팅을 보내는 client ID
        Long receiverId,    // 채팅을 수신하는 client ID
        String content  // 채팅 내용
) {
}
