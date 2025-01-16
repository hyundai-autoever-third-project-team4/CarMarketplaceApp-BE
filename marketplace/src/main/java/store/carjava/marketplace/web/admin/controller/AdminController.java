package store.carjava.marketplace.web.admin.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import store.carjava.marketplace.app.user.dto.UserSummaryDto;
import store.carjava.marketplace.app.user.entity.User;
import store.carjava.marketplace.common.util.user.UserResolver;
import store.carjava.marketplace.web.admin.dto.AdminInfo;
import store.carjava.marketplace.web.admin.service.AdminService;

import java.util.List;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;
    private final UserResolver userResolver;

    @ModelAttribute("adminInfo")
    public AdminInfo populateAdminInfo() {
        User user = userResolver.getCurrentUser();

        return new AdminInfo(
                user.getEmail(),
                user.getRole()
        );
    }

    @ModelAttribute("users")
    public List<UserSummaryDto> users() {
        return adminService.getAllUsers()
                .stream().map(UserSummaryDto::of)
                .toList();
    }

    @GetMapping
    public String adminPage(Model model) {

        Long totalSales = adminService.getTotalSalesAmount();

        model.addAttribute("totalSales", totalSales);
        return "admin/index";
    }

    @GetMapping("/cars")
    public String carsPage() {
        return "admin/cars";
    }

    @GetMapping("/tasks")
    public String tasksPage() {
        return "admin/tasks";
    }

    @GetMapping("/users")
    public String usersPage(Model model) {
        int totalReviews = users().stream().mapToInt(UserSummaryDto::reviewCount).sum();
        int totalSales = users().stream().mapToInt(UserSummaryDto::salesCount).sum();
        int totalPurchases = users().stream().mapToInt(UserSummaryDto::purchaseCount).sum();

        model.addAttribute("totalReviews", totalReviews);
        model.addAttribute("totalSales", totalSales);
        model.addAttribute("totalPurchases", totalPurchases);

        return "admin/users";
    }

    @GetMapping("/car-purchases")
    public String carPurchasesPage() {
        return "admin/car-purchases";
    }

    @GetMapping("/car-sales")
    public String carSalesPage() {
        return "admin/car-sales";
    }

    @GetMapping("/test-drives")
    public String testDrivesPage() {
        return "admin/test-drives";
    }

}
