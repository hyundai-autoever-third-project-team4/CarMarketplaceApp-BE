package store.carjava.marketplace.app.user.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import store.carjava.marketplace.app.user.dto.UserReservationListResponse;
import store.carjava.marketplace.app.user.dto.UserResponse;
import store.carjava.marketplace.app.user.dto.UserSellCarListResponse;
import store.carjava.marketplace.app.user.service.UserService;

@RestController
@RequiredArgsConstructor
@Tag(name = "mypage", description = "마이페이지 관련 api")
public class UserController {

    private final UserService userService;




    @Operation(description = "마이페이지 첫화면-유저 이름 및 가까운 예약 내역")
    @GetMapping("/mypage")
    public ResponseEntity<UserResponse> getUserPage(){
        UserResponse response = userService.getUserPage();
        return ResponseEntity.ok(response);
    }



    @Operation(description = "마이페이지 내가 판매한 차량 리스트")
    @GetMapping("/mypage/sell")
    public ResponseEntity<UserSellCarListResponse> getUserSellList(){
        UserSellCarListResponse response = userService.getUserSellList();
        return ResponseEntity.ok(response);
    }

//
//    @Operation(description = "마이페이지 내가 구매한 차량 리스트")
//    @GetMapping("/mypage/purchase")
//
//
//
    @Operation(description = "마이페이지 시승 예약 내역")
    @GetMapping("/mypage/reservations")
    public ResponseEntity<UserReservationListResponse> getUserReservationList(){
        UserReservationListResponse response = userService.getUserReservationList();
        return ResponseEntity.ok(response);
    }


}
