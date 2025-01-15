package store.carjava.marketplace.app.marketplace_car_image.controller;


import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import store.carjava.marketplace.app.marketplace_car_image.entity.MarketplaceCarImage;
import store.carjava.marketplace.app.marketplace_car_image.service.MarketplaceCarImageService;
import store.carjava.marketplace.common.util.image.ImageUploader;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Transactional
public class MarketplaceCarImageController {

    private final MarketplaceCarImageService marketplaceCarImageService;
    private final ImageUploader imageUploader;

    @PostMapping("/car/{carId}/images")
    public ResponseEntity<List<MarketplaceCarImage>> uploadImages(
            @PathVariable String carId,
            @RequestParam(value = "files", required = false) List<MultipartFile> files) throws IOException {

        // 파일이 없을 경우 예외 처리
        if (files == null || files.isEmpty()) {
            throw new IllegalArgumentException("No files provided.");
        }

        // S3에 파일 업로드 및 URL 생성
        List<String> imageUrls = imageUploader.uploadMultiFiles(files, "marketplace-car-images");

        // 서비스 호출로 DB 저장
        List<MarketplaceCarImage> savedImages = marketplaceCarImageService.saveImages(carId, imageUrls);

        return ResponseEntity.ok(savedImages);
    }
}
