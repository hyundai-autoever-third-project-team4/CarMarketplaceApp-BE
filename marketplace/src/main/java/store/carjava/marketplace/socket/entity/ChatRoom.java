package store.carjava.marketplace.socket.entity;

import lombok.*;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatRoom {
    private String roomId;       // 채팅방 고유 ID
    private String name;         // 채팅방 이름
}
