//package store.carjava.marketplace.socket.controller;
//
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import store.carjava.marketplace.socket.entity.ChatRoom;
//import store.carjava.marketplace.socket.service.ChatRoomService;
//
//import java.util.List;
//import java.util.concurrent.ConcurrentHashMap;
//
//@RestController
//@RequiredArgsConstructor
//public class ChatRoomController {
//
//    private final ChatRoomService chatRoomService;
//
//    // 채팅방 생성 또는 반환 (유저)
//    @PostMapping("/chat/user/rooms")
//    public ResponseEntity<ChatRoom> createOrGetRoom(@RequestParam String userName) {
//        ChatRoom chatRoom = chatRoomService.createRoom(userName);
//        return ResponseEntity.ok(chatRoom);
//    }
//
//    // 모든 채팅방 조회 (관리자)
//    @GetMapping("/chat/admin/rooms")
//    public ResponseEntity<List<ChatRoom>> getAllRooms() {
//        List<ChatRoom> rooms = chatRoomService.findAllRooms();
//        return ResponseEntity.ok(rooms);
//    }
//
//    // 특정 사용자의 채팅방 조회 (유저)
//    @GetMapping("/chat/user/rooms")
//    public ResponseEntity<List<ChatRoom>> getUserRooms(@RequestParam String userName) {
//        List<ChatRoom> rooms = chatRoomService.findUserRooms(userName);
//        return ResponseEntity.ok(rooms);
//    }
//}
//
