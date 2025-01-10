package store.carjava.marketplace.web.admin.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import store.carjava.marketplace.app.user.entity.User;
import store.carjava.marketplace.web.admin.service.AdminService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @GetMapping("/admin")
    public String adminPage(Model model) {
        // 현재 인증된 사용자의 Authentication 객체 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // OidcUser 가져오기
        OidcUser oidcUser = (OidcUser) authentication.getPrincipal();

        // 이메일 및 권한 정보 가져오기
        String email = oidcUser.getAttribute("email"); // 이메일 가져오기

        // 전체 사용자 정보 조회
        List<User> users = adminService.getAllUsers();

        // 모델에 사용자 정보 추가
        model.addAttribute("email", email);
        model.addAttribute("roles", authentication.getAuthorities());

        // 모델에 가입한 유저 정보 추가
        model.addAttribute("users", users);

        return "admin/index";
    }
}
