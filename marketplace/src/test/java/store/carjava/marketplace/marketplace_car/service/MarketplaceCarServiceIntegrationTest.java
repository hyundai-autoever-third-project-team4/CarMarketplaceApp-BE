package store.carjava.marketplace.marketplace_car.service;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.transaction.annotation.Transactional;
import store.carjava.marketplace.app.marketplace_car.dto.MarketplaceCarRegisterRequest;
import store.carjava.marketplace.app.marketplace_car.entity.MarketplaceCar;
import store.carjava.marketplace.app.marketplace_car.repository.MarketplaceCarRepository;
import store.carjava.marketplace.app.marketplace_car.service.MarketplaceCarService;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@WithMockUser(username = "dnrals7929@naver.com", roles = {"USER"})
public class MarketplaceCarServiceIntegrationTest { // 통합테스트는 모의객체로 테스트 안함.

    @Autowired
    private MarketplaceCarService marketplaceCarService;

    @Autowired
    private MarketplaceCarRepository marketplaceCarRepository;

    @Test
    void testSellRegisterCar() {
        // Given
        MarketplaceCarRegisterRequest request = new MarketplaceCarRegisterRequest("528주5649", "송민재");

        // When
        marketplaceCarService.sellRegisterCar(request);

        // Then
        MarketplaceCar car = marketplaceCarRepository.findById(request.licensePlate()).orElseThrow();
        assertNotNull(car);
        assertEquals("PENDING_SALE", car.getStatus());
    }
}
