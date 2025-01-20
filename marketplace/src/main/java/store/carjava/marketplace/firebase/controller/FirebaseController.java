package store.carjava.marketplace.firebase.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import store.carjava.marketplace.firebase.dto.GetTokenRequest;
import store.carjava.marketplace.firebase.service.FirebaseService;

@RestController
@RequiredArgsConstructor
public class FirebaseController {
    private final FirebaseService firebaseService;

    @Operation(summary = "fcm 토큰 저장", description = "네이티브에서 fcm 토큰을 전달받아 저장하거나 갱신한다")
    @PostMapping("/fcm/save")
    public ResponseEntity<String> getTokenFromNative(
            @RequestBody GetTokenRequest payload
    ){
        firebaseService.getToken(payload);

        return ResponseEntity.ok("token saved");
    }

    @Operation(summary = "fcm 토큰 삭제", description = "fcm 토큰을 삭제한다")
    @DeleteMapping("/fcm/delete/{token}")
    public ResponseEntity<String> deleteFCMToken(
            @PathVariable String token
    ){
        firebaseService.deleteToken(token);

        return ResponseEntity.ok("token deleted");
    }
}
