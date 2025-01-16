package store.carjava.marketplace.socket.service;

import org.springframework.stereotype.Service;
import store.carjava.marketplace.socket.entity.ChatRoom;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;
import store.carjava.marketplace.socket.entity.ChatRoom;

import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Service
public class ChatRoomService {

    // 사용자별 채팅방 관리
    private final Map<String, Set<ChatRoom>> userChatRooms = new ConcurrentHashMap<>();
    private final Set<ChatRoom> allChatRooms = Collections.newSetFromMap(new ConcurrentHashMap<>());

    // 채팅방 생성 (유저)
    public ChatRoom createOrGetRoom(String userName) {
        userChatRooms.putIfAbsent(userName, new HashSet<>());
        Set<ChatRoom> userRooms = userChatRooms.get(userName);

        // 유저가 이미 소유한 방이 있으면 반환
        if (!userRooms.isEmpty()) {
            return userRooms.iterator().next();
        }

        // 새로운 방 생성
        String roomId = "room-" + System.currentTimeMillis(); // 유니크한 ID 생성
        ChatRoom chatRoom = new ChatRoom(roomId, userName);
        userRooms.add(chatRoom);
        allChatRooms.add(chatRoom);
        return chatRoom;
    }

    // 모든 채팅방 조회 (관리자)
    public List<ChatRoom> findAllRooms() {
        return List.copyOf(allChatRooms);
    }

    // 특정 사용자의 채팅방 조회
    public List<ChatRoom> findUserRooms(String userName) {
        return userChatRooms.getOrDefault(userName, new HashSet<>())
                .stream()
                .toList();
    }
}
