package store.carjava.marketplace.app.marketplace_car_image.service;


import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import store.carjava.marketplace.app.marketplace_car.entity.MarketplaceCar;
import store.carjava.marketplace.app.marketplace_car.repository.MarketplaceCarRepository;
import store.carjava.marketplace.app.marketplace_car_image.entity.MarketplaceCarImage;
import store.carjava.marketplace.app.marketplace_car_image.repository.MarketplaceCarImageRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MarketplaceCarImageService {

    private final MarketplaceCarRepository marketplaceCarRepository;
    private final MarketplaceCarImageRepository marketplaceCarImageRepository;

    public List<MarketplaceCarImage> saveImages(String carId, List<String> imageUrls) {
        // 차량 정보 가져오기
        MarketplaceCar marketplaceCar = marketplaceCarRepository.findById(carId)
                .orElseThrow(() -> new IllegalArgumentException("MarketplaceCar not found with id: " + carId));

        // 이미지 URL 리스트를 엔티티로 변환 및 저장
        List<MarketplaceCarImage> images = imageUrls.stream()
                .map(imageUrl -> MarketplaceCarImage.builder()
                        .marketplaceCar(marketplaceCar)
                        .imageUrl(imageUrl)
                        .build())
                .collect(Collectors.toList());

        return marketplaceCarImageRepository.saveAll(images);
    }


}
