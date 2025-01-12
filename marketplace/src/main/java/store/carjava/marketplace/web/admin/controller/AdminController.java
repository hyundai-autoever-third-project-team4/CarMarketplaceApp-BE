package store.carjava.marketplace.web.admin.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import store.carjava.marketplace.app.user.entity.User;
import store.carjava.marketplace.web.admin.service.AdminService;

import java.util.List;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @GetMapping
    public String adminPage(Model model) {
        // 현재 인증된 사용자의 Authentication 객체 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication.getPrincipal() instanceof UserDetails userDetails) {
            // 이메일 및 권한 정보 가져오기
            String email = userDetails.getUsername();

            // 전체 사용자 정보 조회
            List<User> users = adminService.getAllUsers();

            // 모델에 사용자 정보 추가
            model.addAttribute("email", email);
            model.addAttribute("roles", authentication.getAuthorities());

            // 모델에 가입한 유저 정보 추가
            model.addAttribute("users", users);

            return "admin/index";
        } else {
            // 인증 객체가 UserDetails가 아닌 경우 예외 처리
            throw new IllegalStateException("현재 인증된 사용자가 UserDetails 타입이 아닙니다.");
        }
    }

    @GetMapping("/cars")
    public String carsPage(Model model) {
        return "admin/cars";
    }

    @GetMapping("/tasks")
    public String tasksPage(Model model) {
        return "admin/tasks";
    }

    @GetMapping("/users")
    public String usersPage(Model model) {
        return "admin/users";
    }

    @GetMapping("/car-purchases")
    public String carPurchasesPage(Model model) {
        return "admin/car-purchases";
    }

    @GetMapping("/car-sales")
    public String carSalesPage(Model model) {
        return "admin/car-sales";
    }

    @GetMapping("/test-drives")
    public String testDrivesPage(Model model) {
        return "admin/test-drives";
    }
}
