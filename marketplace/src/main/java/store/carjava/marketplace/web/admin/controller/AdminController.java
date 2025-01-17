package store.carjava.marketplace.web.admin.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import store.carjava.marketplace.app.marketplace_car.dto.MarketplaceCarSummaryDto;
import store.carjava.marketplace.app.user.dto.UserSummaryDto;
import store.carjava.marketplace.app.user.entity.User;
import store.carjava.marketplace.common.util.user.UserResolver;
import store.carjava.marketplace.web.admin.dto.AdminInfo;
import store.carjava.marketplace.web.admin.service.AdminService;

import java.util.List;

@Slf4j
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

    @ModelAttribute("cars")
    public Page<MarketplaceCarSummaryDto> cars(
            @RequestParam(value = "licensePlate", required = false) String licensePlate,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {
        // 검색 조건이 없으면 전체 차량 반환
        if (licensePlate != null && !licensePlate.isBlank()) {
            return adminService.searchCarsByLicensePlate(licensePlate, page, size);
        }
        // 검색 조건이 없으면 전체 차량 반환
        return adminService.getCars(page, size);
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
    public String carPurchasesPage(Model model) {
        model.addAttribute("pendingPurchases", adminService.getPendingPurchaseHistory());
        model.addAttribute("completedPurchases", adminService.getCompletedPurchaseHistory());
        return "admin/car-purchases";

    }

    // 차량 구매 승인
    @PostMapping("/car-purchases/{id}/approve")
    @ResponseBody
    public ResponseEntity<String> approvePurchase(@PathVariable Long id) {
        try {
            adminService.approvePurchase(id);
            return ResponseEntity.ok("Success");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @GetMapping("/car-sales")
    public String carSalesPage(Model model) {
        model.addAttribute("pendingSales", adminService.getPendingSaleHistory());
        return "admin/car-sales";
    }

    @GetMapping("/test-drives")
    public String testDrivesPage() {
        return "admin/test-drives";
    }

}
