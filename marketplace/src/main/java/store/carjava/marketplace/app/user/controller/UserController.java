package store.carjava.marketplace.app.user.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import store.carjava.marketplace.app.review.dto.ReviewInfoListDto;
import store.carjava.marketplace.app.user.dto.UserResponse;
import store.carjava.marketplace.app.user.service.UserService;

public class UserController {

    private final UserService userService;




    @Operation(description = "마이페이지 첫화면-유저 이름 및 가까운 예약 내역")
    @GetMapping("/mypage")
    public ResponseEntity<UserResponse> getUserPage(){
        UserResponse response = userService.getUserPage();
        return ResponseEntity.ok(response);
    }


    @GetMapping("/mypage/like")

    @GetMapping("/mypage/sell")

    @GetMapping("/mypage/purchase")



    @GetMapping("/mypage/reservation")

    @GetMapping("/mypage/review")
}
