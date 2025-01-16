package store.carjava.marketplace.socket.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import store.carjava.marketplace.socket.entity.ChatRoom;
import store.carjava.marketplace.socket.service.ChatRoomService;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequiredArgsConstructor
public class ChatRoomController {

    private final ChatRoomService chatRoomService;

    // 채팅방 생성
    @PostMapping("/rooms")
    public ResponseEntity<ChatRoom> createRoom(@RequestParam String userName) {
        ChatRoom chatRoom = chatRoomService.createRoom(userName);
        return ResponseEntity.ok(chatRoom);
    }

    // 모든 채팅방 조회
    @GetMapping("/rooms")
    public ResponseEntity<List<ChatRoom>> getAllRooms() {
        List<ChatRoom> rooms = chatRoomService.findAllRooms();
        return ResponseEntity.ok(rooms);
    }
}
