package store.carjava.marketplace.app.test;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    // 인증된 사용자 전용 API
    @GetMapping
    public String userAccess() {
        return "Welcome, User! You are authenticated.";
    }
}
