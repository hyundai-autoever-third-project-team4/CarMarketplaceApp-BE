package store.carjava.marketplace.app.like.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import store.carjava.marketplace.app.like.dto.LikeResponse;
import store.carjava.marketplace.app.like.dto.MyLikeCarListResponse;
import store.carjava.marketplace.app.like.service.LikeService;

@RestController
@RequiredArgsConstructor
@Tag(name="좋아요 (찜) API", description = "차량 찜하기 관련 API를 제공합니다.")
public class LikeController {
    private final LikeService likeService;

    @Operation(summary = "차량 찜하기", description = "차량 찜하기")
    @PostMapping("/like/{carId}")
    public ResponseEntity<LikeResponse> toggleLike(@PathVariable String carId) {
        LikeResponse response = likeService.toggleLike(carId);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "내가 찜한 차량 목록 조회", description = "마이페이지에서 내가 찜한 차량 목록 조회.")
    @GetMapping("/mypage/like")
    public ResponseEntity<MyLikeCarListResponse> getMyLikeCars() {
        MyLikeCarListResponse myLikeCarList = likeService.getMyLikeCars();
        return ResponseEntity.ok(myLikeCarList);
    }
}
