package store.carjava.marketplace.app.like.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import store.carjava.marketplace.app.like.dto.LikeResponse;
import store.carjava.marketplace.app.like.service.LikeService;

@RestController
@RequiredArgsConstructor
@Tag(name="like", description = "차량 찜하기")
public class LikeController {
    private final LikeService likeService;

    @PostMapping("/like/{carId}")
    public ResponseEntity<LikeResponse> toggleLike(@PathVariable String carId) {
        LikeResponse response = likeService.toggleLike(carId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/mypage/like")
    public
}
