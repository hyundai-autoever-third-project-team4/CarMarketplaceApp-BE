package store.carjava.marketplace.app.user.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import store.carjava.marketplace.app.like.dto.MyLikeCarListResponse;
import store.carjava.marketplace.app.like.service.LikeService;
import store.carjava.marketplace.app.review.dto.ReviewDeleteResponse;
import store.carjava.marketplace.app.review.dto.ReviewInfoListDto;
import store.carjava.marketplace.app.review.service.ReviewService;
import store.carjava.marketplace.app.user.dto.*;
import store.carjava.marketplace.app.user.service.UserService;

@RestController
@RequiredArgsConstructor
@Validated
@Tag(name = "사용자 마이페이지 API", description = "사용자의 마이페이지 관련 api를 제공합니다.")
public class UserController {

    private final UserService userService;
    private final LikeService likeService;
    private final ReviewService reviewService;


    @Operation(summary = "유저정보 조회", description = "로그인한 유저의 정보")
    @GetMapping("/userInfo")
    public ResponseEntity<UserInfoResponse> getCurrentUserInfo() {
        UserInfoResponse response = userService.getCurrentUserInfo();
        return ResponseEntity.ok(response);
    }


    @Operation(summary = "유저정보 추가저장", description = "유저 이름, 주소, 전화번호 저장")
    @PutMapping("/profileUpdate")
    public ResponseEntity<UserProfileUpdateResponse> updateUserProfile(
            @Valid @RequestBody UserProfileUpdateRequest request
    ){
        UserProfileUpdateResponse response = userService.updateUserProfile(request);
        return ResponseEntity.ok(response);

    }

    @Operation(summary = "마이페이지 첫화면", description = "마이페이지 첫화면-유저 이름 및 가까운 예약 내역")
    @GetMapping("/mypage")
    public ResponseEntity<UserResponse> getUserPage(){
        UserResponse response = userService.getUserPage();
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "마이페이지 내가 판 차", description = "마이페이지 내가 판매한 차량 리스트")
    @GetMapping("/mypage/sell")
    public ResponseEntity<UserSellCarListResponse> getUserSellList(){
        UserSellCarListResponse response = userService.getUserSellList();
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "내가 구매한 차", description = "마이페이지 내가 구매한 차량 리스트")
    @GetMapping("/mypage/purchase")
    public ResponseEntity<UserPurchaseListResponse> getUserPurchaseList(){
        UserPurchaseListResponse response = userService.getUserPurchaseList();
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "유저 시승 예약 내역", description = "마이페이지 시승 예약 내역")
    @GetMapping("/mypage/reservations")
    public ResponseEntity<UserReservationListResponse> getUserReservationList(){
        UserReservationListResponse response = userService.getUserReservationList();
        return ResponseEntity.ok(response);
    }


    @Operation(summary = "내가 찜한 차량 목록 조회", description = "마이페이지에서 내가 찜한 차량 목록 조회.")
    @GetMapping("/mypage/like")
    public ResponseEntity<MyLikeCarListResponse> getMyLikeCars() {
        MyLikeCarListResponse myLikeCarList = likeService.getMyLikeCars();
        return ResponseEntity.ok(myLikeCarList);
    }

    @Operation(summary = "내가 쓴 리뷰 조회", description = "마이페이지에서 나의 리뷰 조회 api")
    @GetMapping("/mypage/reviews")
    public ResponseEntity<ReviewInfoListDto> getUserReviews(
    ) {
        ReviewInfoListDto userReviews = reviewService.getMyReviews();
        return ResponseEntity.ok(userReviews);
    }


    @Operation(summary = "내가 쓴 리뷰 삭제", description = "마이페이지 리뷰 삭제")
    @DeleteMapping("mypage/reviews/{reviewId}")
    public ResponseEntity<ReviewDeleteResponse> deleteReview(
            @Parameter(description = "삭제할 리뷰 ID") @PathVariable("reviewId") Long reviewId
    ) {
        ReviewDeleteResponse responseDto = reviewService.deleteReview(reviewId);
        return ResponseEntity.ok(responseDto);
    }
}
