//package store.carjava.marketplace.socket.service;
//
//import org.springframework.stereotype.Service;
//import store.carjava.marketplace.socket.entity.ChatRoom;
//
//import java.util.*;
//import java.util.concurrent.ConcurrentHashMap;
//
//import org.springframework.stereotype.Service;
//import store.carjava.marketplace.socket.entity.ChatRoom;
//import store.carjava.marketplace.socket.repository.ChatRoomRepository;
//
//import java.util.concurrent.ConcurrentHashMap;
//import java.util.stream.Collectors;
//
//@Service
//public class ChatRoomService {
//
//    private final ChatRoomRepository chatRoomRepository;
//
//    public ChatRoomService(ChatRoomRepository chatRoomRepository) {
//        this.chatRoomRepository = chatRoomRepository;
//    }
//
//    // 채팅방 생성 (유저별)
//    public ChatRoom createRoom(String userName) {
//        // 유저가 이미 소유한 방이 있는지 확인
//        List<ChatRoom> existingRooms = chatRoomRepository.findByName(userName);
//        if (!existingRooms.isEmpty()) {
//            return existingRooms.get(0); // 첫 번째 방 반환
//        }
//
//        // 새로운 방 생성
//        String roomId = "room-" + System.currentTimeMillis(); // 유니크 ID 생성
//        ChatRoom newRoom = new ChatRoom(roomId, userName);
//        return chatRoomRepository.save(newRoom); // DB에 저장
//    }
//
//    // 모든 채팅방 조회 (관리자)
//    public List<ChatRoom> findAllRooms() {
//        return chatRoomRepository.findAll(); // DB에서 모든 방 조회
//    }
//
//    // 특정 사용자의 채팅방 조회
//    public List<ChatRoom> findUserRooms(String userName) {
//        return chatRoomRepository.findByName(userName); // 소유자 기준 검색
//    }
//
//    // 채팅방 삭제
//    public void deleteRoom(String roomId) {
//        Optional<ChatRoom> chatRoom = chatRoomRepository.findById(roomId);
//        chatRoom.ifPresent(chatRoomRepository::delete); // 존재하면 삭제
//    }
//}
