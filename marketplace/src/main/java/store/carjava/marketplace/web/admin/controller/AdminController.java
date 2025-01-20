package store.carjava.marketplace.web.admin.controller;

import io.swagger.v3.oas.annotations.Operation;

import java.util.ArrayList;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import store.carjava.marketplace.app.marketplace_car.dto.MarketplaceCarSummaryDto;
import store.carjava.marketplace.app.reservation.dto.ReservationDetailDto;
import store.carjava.marketplace.app.user.dto.UserSummaryDto;
import store.carjava.marketplace.app.user.entity.User;
import store.carjava.marketplace.common.util.user.UserResolver;
import store.carjava.marketplace.web.admin.dto.AdminInfo;
import store.carjava.marketplace.web.admin.dto.CarPurchaseDto;
import store.carjava.marketplace.web.admin.dto.CarSellDto;
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
                user.getId(),
                user.getEmail(),
                user.getRole()
        );
    }

    @ModelAttribute("users")
    public Page<UserSummaryDto> users(
            @RequestParam(value = "email", required = false) String email,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {
        // 검색 조건이 있을 경우 이메일로 검색
        if (email != null && !email.isBlank()) {
            return adminService.searchUsersByEmail(email, page, size);
        }
        // 검색 조건이 없을 경우 전체 사용자 반환
        return adminService.getUsers(page, size);
    }


    @ModelAttribute("cars")
    public Page<MarketplaceCarSummaryDto> cars(
            @RequestParam(value = "licensePlate", required = false) String licensePlate,
            @RequestParam(value = "model", required = false) String model,
            @RequestParam(value = "status", required = false) String status,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {
        // 검색 조건이 없으면 전체 차량 반환
        if (licensePlate != null && !licensePlate.isBlank()) {
            return adminService.searchCarsByLicensePlate(licensePlate, page, size);
        }

        // 모델 검색 조건이 있는 경우
        if (model != null && !model.isBlank()) {
            return adminService.searchCarsByModel(model, page, size);
        }

        // 상태 검색 조건
        if (status != null && !status.isBlank()) {
            return adminService.searchCarsByStatus(status, page, size);
        }
        // 검색 조건이 없으면 전체 차량 반환
        return adminService.getCars(page, size);
    }

    @ModelAttribute("purchaseTasks")
    public List<CarPurchaseDto> carPurchaseDtos() {
        return adminService.getCarPurchases();
    }

    @ModelAttribute("salesTasks")
    public List<CarSellDto> carSalesHistoryInfoDtos() {
        return adminService.getCarSales();
    }

    @ModelAttribute("reservations")
    public Page<ReservationDetailDto> populateReservations(
            @RequestParam(value = "reservationName", required = false) String reservationName,
            @RequestParam(value = "licensePlate", required = false) String licensePlate,
            @RequestParam(value = "status", required = false) String status,
            @RequestParam(value = "reservationDate", required = false) String reservationDate,
            @RequestParam(value = "page", required = false) Integer page
    ) {
        int pageNumber = (page != null && page >= 0) ? page : 0;
        Pageable pageable = PageRequest.of(pageNumber, 10); // 페이지당 10개 항목
        return adminService.getReservationDetails(reservationName, licensePlate, status, reservationDate, pageable);
    }

    @ModelAttribute("carModels")
    public List<String> carModels() {
        // 모든 차량 모델 목록을 중복 없이 가져옵니다
        return adminService.getDistinctCarModels();
    }

    @GetMapping()
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
    public String tasksPage(Model model) {
        Long purchaseRequests = adminService.getTotalPurchases();
        Long salesRequests = adminService.getTotalSales();

        // 모델에 데이터 추가
        model.addAttribute("purchaseRequests", purchaseRequests);
        model.addAttribute("salesRequests", salesRequests);

        return "admin/tasks";
    }

    @GetMapping("/users")
    public String usersPage(Model model) {
        Long totalReviews = adminService.getTotalReviews();
        Long totalSales = adminService.getTotalSales();
        Long totalPurchases = adminService.getTotalPurchases();

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

    @Operation(summary = "차량 판매 승인", description = "특정 차량의 판매를 승인합니다.")
    @PutMapping("/car-sales/approve")
    public ResponseEntity<String> approveCar(
            @RequestParam(name = "carId") String carId,
            @RequestParam(name = "testDriveCenterId") Long testDriveCenterId,
            @RequestParam(name = "price") Long price,
            @RequestParam(name = "exteriorImages", required = false) List<MultipartFile> exteriorImages,
            @RequestParam(name = "interiorImages", required = false) List<MultipartFile> interiorImages,
            @RequestParam(name = "wheelImages", required = false) List<MultipartFile> wheelImages,
            @RequestParam(name = "additionalImages", required = false) List<MultipartFile> additionalImages
    ) {
        try {
            // 모든 이미지 파일들을 하나의 리스트로 통합
            List<MultipartFile> allImages = new ArrayList<>();
            if (exteriorImages != null) allImages.addAll(exteriorImages);
            if (interiorImages != null) allImages.addAll(interiorImages);
            if (wheelImages != null) allImages.addAll(wheelImages);
            if (additionalImages != null) allImages.addAll(additionalImages);


            adminService.approveCar(carId, testDriveCenterId, price, allImages);
            return ResponseEntity.ok("차량 판매가 승인되었습니다.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("서버 오류가 발생했습니다. 나중에 다시 시도해주세요.");
        }
    }

    @GetMapping("/car-sales")
    public String carSalesPage(Model model) {
        model.addAttribute("pendingSales", adminService.getPendingSaleHistory());
        model.addAttribute("approvedSales", adminService.getApprovedSaleHistory());
        return "admin/car-sales";
    }

    @GetMapping("/reservations")
    public String testDrivesPage() {
        return "admin/reservations";
    }


    @GetMapping("/chat")
    public String showChatPage() {
        // 현재 사용자 정보 가져오기
        return "admin/chat";
    }
}
