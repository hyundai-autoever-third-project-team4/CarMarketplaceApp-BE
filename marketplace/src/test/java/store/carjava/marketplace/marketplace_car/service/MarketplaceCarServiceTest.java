package store.carjava.marketplace.marketplace_car.service;


import org.antlr.v4.runtime.tree.pattern.ParseTreePattern;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.web.client.ExpectedCount;
import store.carjava.marketplace.app.like.repository.LikeRepository;
import store.carjava.marketplace.app.marketplace_car.dto.MarketplaceCarResponse;
import store.carjava.marketplace.app.marketplace_car.entity.MarketplaceCar;
import store.carjava.marketplace.app.marketplace_car.exception.MaxPriceLessThanMinPriceException;
import store.carjava.marketplace.app.marketplace_car.exception.MinPriceExceedsMaxPriceException;
import store.carjava.marketplace.app.marketplace_car.exception.StatusNotFoundException;
import store.carjava.marketplace.app.marketplace_car.repository.MarketplaceCarRepository;
import store.carjava.marketplace.app.marketplace_car.service.MarketplaceCarService;
import store.carjava.marketplace.common.util.user.UserResolver;
import store.carjava.marketplace.marketplace_car.builder.MarketplaceCarTestBuilder;

import java.util.Collections;
import java.util.List;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MarketplaceCarServiceTest {


    @InjectMocks
    private MarketplaceCarService marketplaceCarService;

    @Mock
    private MarketplaceCarRepository marketplaceCarRepository;

    @Mock
    private LikeRepository likeRepository;
    @Mock
    private UserResolver userResolver;
    @BeforeEach
    void setUp() {
        // 초기화 작업 (필요한 경우)
    }


    // 연료 타입 관련한 테스트 코드
    @Test
    void testIsValidFuelType_ShouldReturnTrue_WhenFuelTypeValid() {
        // given
        String validFuelType1 = "가솔린";
        String validFuelType2 = "디젤";
        String validFuelType3 = "전기";
        String validFuelType4 = "하이브리드";

        // when & then
        assertThat(marketplaceCarService.isValidFuelType(validFuelType1)).isTrue();
        assertThat(marketplaceCarService.isValidFuelType(validFuelType2)).isTrue();
        assertThat(marketplaceCarService.isValidFuelType(validFuelType3)).isTrue();
        assertThat(marketplaceCarService.isValidFuelType(validFuelType4)).isTrue();
    }

    @Test
    void testIsValidFuelType_ShouldReturnFalse_WhenFuelTypeInvalid() {
        //given
        String vaildFuelType1 = "수소";
        String vaildFuelType2 = "연료";
        String vaildFuelType3 = "가스";

        //when, then
        assertThat(marketplaceCarService.isValidFuelType(vaildFuelType1)).isFalse();
        assertThat(marketplaceCarService.isValidFuelType(vaildFuelType2)).isFalse();
        assertThat(marketplaceCarService.isValidFuelType(vaildFuelType3)).isFalse();
    }

    @Test
    void testIsValidFuelType_ShouldReturnFalse_WhenFuelTypeIsNullOrEmpty() {

        // given
        String nullFuelType = null;
        String emptyFuelType = "";

        // when & then
        assertThat(marketplaceCarService.isValidFuelType(nullFuelType)).isFalse();
        assertThat(marketplaceCarService.isValidFuelType(emptyFuelType)).isFalse();

    }

    //상태 테스트 코드

    @Test
    void isValidStatus_ShouldReturnTrue_ForValidStatus(){
        //given
        String validStatus1 = "AVAILABLE_FOR_PURCHASE";
        String validStatus2 = "PENDING_PURCHASE_APPROVAL";
        String validStatus3 = "NOT_AVAILABLE_FOR_PURCHASE";
        String validStatus4 = "PENDING_SALE";
        String validStatus5 = "SALE_APPROVED";

        //when, then
        assertThat(marketplaceCarService.isValidStatus(validStatus1)).isTrue();
        assertThat(marketplaceCarService.isValidStatus(validStatus2)).isTrue();
        assertThat(marketplaceCarService.isValidStatus(validStatus3)).isTrue();
        assertThat(marketplaceCarService.isValidStatus(validStatus4)).isTrue();
        assertThat(marketplaceCarService.isValidStatus(validStatus5)).isTrue();
    }


    @Test
    void isValidStatus_ShouldReturnFalse_ForInvalidStatus(){
        //given
        String invalidStatus1 = "INVALID_STATUS";
        String invalidStatus2 = "UNKNOWN";
        String invalidStatus3 = "PENDING";

        assertThat(marketplaceCarService.isValidStatus(invalidStatus1)).isFalse();
        assertThat(marketplaceCarService.isValidStatus(invalidStatus2)).isFalse();
        assertThat(marketplaceCarService.isValidStatus(invalidStatus3)).isFalse();
    }

    @Test
    void isValidStatus_ShouldReturnFalse_ForNullStatus(){
        //given
        String nullStatus = null;
        String emptyStatus = "";
        //when
        assertThat(marketplaceCarService.isValidStatus(nullStatus)).isFalse();
        assertThat(marketplaceCarService.isValidStatus(emptyStatus)).isFalse();
    }

    @Test
    void getFilteredCars_ShouldThrowException_WhenMinPriceExceedsMaxPrice() {
        // given
        Long minPrice = 5000L;
        Long maxPrice = 3000L;

        // when & then
        assertThatThrownBy(() -> marketplaceCarService.getFilteredCars(
                null, null, null, null, null, null, null,
                null, null, null, maxPrice, minPrice,
                null, null, null, null, null, null,
                null, null, null, null, null, Pageable.unpaged()
        )).isInstanceOf(MinPriceExceedsMaxPriceException.class);
    }

    // minYear 가 maxYear 넘어갈 수 없다.
    @Test
    void getFilteredCars_ShouldThrowException_WhenMinModelYearExceedsMaxModelYear() {
        // given
        Integer minModelYear = 2025;
        Integer maxModelYear = 2020;

        // when & then
        assertThatThrownBy(() -> marketplaceCarService.getFilteredCars(
                null, null, null, null, null, null, null,
                null, null, null, null, null, null,
                null, minModelYear, maxModelYear, null, null,
                null, null, null, null, null, Pageable.unpaged()
        )).isInstanceOf(MaxPriceLessThanMinPriceException.class);
    }


    @Test
    void getFilteredCars_ShouldThrowException_WhenInvalidStatusProvided() {
        // given
        String invalidStatus = "INVALID_STATUS";

        // when & then
        assertThatThrownBy(() -> marketplaceCarService.getFilteredCars(
                null, null, null, null, null, null, null,
                null, null, null, null, null, null,
                null, null, null, null, null,
                invalidStatus, null, null, null, null, Pageable.unpaged()
        )).isInstanceOf(StatusNotFoundException.class);
    }

    // 차가 모두 비어있을때 아무것도 리턴하지 않는걸 확인하는 test code
    @Test
    void getFilteredCars_ShouldReturnEmptyList_WhenNoCarsMatchFilters() {
        // given
        Page<MarketplaceCar> emptyPage = new PageImpl<>(Collections.emptyList());
        when(marketplaceCarRepository.filterCars(any(), any(), any(), any(), any(), any(), any(),
                any(), any(), any(), any(), any(), any(), any(), any(), any(), any(), any(), any(),
                any(), any(), any(), any(), any()))
                .thenReturn(emptyPage);

        // when
        List<MarketplaceCarResponse> result = marketplaceCarService.getFilteredCars(
                null, null, null, null, null, null, null,
                null, null, null, null, null, null,
                null, null, null, null, null,
                null, null, null, null, null, Pageable.unpaged()
        );

        // then
        assertThat(result).isEmpty(); // 결과가 빈 리스트인지 검증
    }



}
