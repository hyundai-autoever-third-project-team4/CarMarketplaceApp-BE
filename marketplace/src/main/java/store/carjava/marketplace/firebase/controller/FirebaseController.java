package store.carjava.marketplace.firebase.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import store.carjava.marketplace.firebase.dto.GetTokenRequest;
import store.carjava.marketplace.firebase.service.FirebaseService;

@RestController
@RequiredArgsConstructor
public class FirebaseController {
    private final FirebaseService firebaseService;

    @Operation(summary = "fcm 토큰 받기", description = "네이티브에서 fcm 토큰을 전달받는다")
    @PostMapping("/firebase/token")
    public ResponseEntity<String> getTokenFromNative(
            @RequestBody GetTokenRequest payload
    ){
        firebaseService.getToken(payload);

        return ResponseEntity.ok("token saved");
    }
}
