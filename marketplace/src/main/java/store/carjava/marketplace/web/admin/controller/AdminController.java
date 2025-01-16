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

    @GetMapping
    public String adminPage(Model model) {
        List<User> users = adminService.getAllUsers();

        Long totalSales = adminService.getTotalSalesAmount();

        model.addAttribute("users", users);
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
    public String usersPage() {
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
