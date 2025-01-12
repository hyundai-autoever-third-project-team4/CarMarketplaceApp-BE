package store.carjava.marketplace.app.test;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminController {

    // 관리자 전용 API
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')") // ROLE_ADMIN 권한이 있는 사용자만 접근 가능
    public String adminAccess() {
        return "Welcome, Admin! You have access to admin resources.";
    }
}
