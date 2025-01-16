package store.carjava.marketplace.socket.service;

import org.springframework.stereotype.Service;
import store.carjava.marketplace.socket.entity.ChatRoom;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;
import store.carjava.marketplace.socket.entity.ChatRoom;

import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Service
public class ChatRoomService {

    private final Map<String, ChatRoom> chatRooms = new HashMap<>();

    // 채팅방 생성
    public ChatRoom createRoom(String userName) {
        String roomId = "room-" + System.currentTimeMillis(); // 유니크한 ID 생성
        ChatRoom chatRoom = new ChatRoom(roomId, userName);
        chatRooms.put(roomId, chatRoom);
        return chatRoom;
    }

    // 모든 채팅방 조회
    public List<ChatRoom> findAllRooms() {
        return Collections.unmodifiableList(chatRooms.values().stream().collect(Collectors.toList()));
    }
}
