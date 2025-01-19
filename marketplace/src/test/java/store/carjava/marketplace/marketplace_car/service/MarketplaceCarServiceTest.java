package store.carjava.marketplace.marketplace_car.service;


import org.antlr.v4.runtime.tree.pattern.ParseTreePattern;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.client.ExpectedCount;
import store.carjava.marketplace.app.base_car.entity.BaseCar;
import store.carjava.marketplace.app.base_car.repository.BaseCarRepository;
import store.carjava.marketplace.app.like.entity.Like;
import store.carjava.marketplace.app.like.repository.LikeRepository;
import store.carjava.marketplace.app.marketplace_car.dto.MarketplaceCarResponse;
import store.carjava.marketplace.app.marketplace_car.entity.MarketplaceCar;
import store.carjava.marketplace.app.marketplace_car.exception.*;
import store.carjava.marketplace.app.marketplace_car.repository.MarketplaceCarRepository;
import store.carjava.marketplace.app.marketplace_car.service.MarketplaceCarService;
import store.carjava.marketplace.app.user.entity.User;
import store.carjava.marketplace.app.vo.CarDetails;
import store.carjava.marketplace.common.util.user.UserResolver;
import store.carjava.marketplace.marketplace_car.builder.MarketplaceCarTestBuilder;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
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

    @Mock
    private SecurityContext securityContext;


    @Mock
    private BaseCarRepository baseCarRepository;

    @Mock
    private Authentication authentication;

    @Mock
    private User currentUser;
    @BeforeEach
    void setUp() {
        // Mock SecurityContextHolder
        SecurityContextHolder.setContext(securityContext);

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


    // 좋아요 갯수별 정렬 top 5 Car 조회
    @Test
    void getTop5CarsByLikes_returnsTop5Cars() {
        // Given
        CarDetails carDetails1 = CarDetails.builder()
                .brand("Brand1")
                .model("Model1")
                .name("Car1")
                .build();

        CarDetails carDetails2 = CarDetails.builder()
                .brand("Brand2")
                .model("Model2")
                .name("Car2")
                .build();

        CarDetails carDetails3 = CarDetails.builder()
                .brand("Brand3")
                .model("Model3")
                .name("Car3")
                .build();

        CarDetails carDetails4 = CarDetails.builder()
                .brand("Brand4")
                .model("Model4")
                .name("Car4")
                .build();

        CarDetails carDetails5 = CarDetails.builder()
                .brand("Brand5")
                .model("Model5")
                .name("Car5")
                .build();

        CarDetails carDetails6 = CarDetails.builder()
                .brand("Brand6")
                .model("Model6")
                .name("Car6")
                .build();

        MarketplaceCar car1 = MarketplaceCar.builder()
                .id("1")
                .price(10000L)
                .status("AVAILABLE_FOR_PURCHASE")
                .carDetails(carDetails1) // 초기화
                .likes(List.of(new Like(), new Like())) // 2 likes
                .build();

        MarketplaceCar car2 = MarketplaceCar.builder()
                .id("2")
                .price(20000L)
                .status("AVAILABLE_FOR_PURCHASE")
                .carDetails(carDetails2)// 초기화
                .likes(List.of(new Like())) // 1 like
                .build();

        MarketplaceCar car3 = MarketplaceCar.builder()
                .id("3")
                .price(30000L)
                .status("AVAILABLE_FOR_PURCHASE")
                .carDetails(carDetails3) // 초기화
                .likes(List.of(new Like(), new Like(), new Like())) // 3 likes
                .build();

        MarketplaceCar car4 = MarketplaceCar.builder()
                .id("4")
                .price(40000L)
                .status("AVAILABLE_FOR_PURCHASE")
                .carDetails(carDetails4) // 초기화
                .likes(List.of()) // 0 likes
                .build();

        MarketplaceCar car5 = MarketplaceCar.builder()
                .id("5")
                .price(50000L)
                .status("AVAILABLE_FOR_PURCHASE")
                .carDetails(carDetails5) // 초기화
                .likes(List.of(new Like(), new Like())) // 2 likes
                .build();

        MarketplaceCar car6 = MarketplaceCar.builder()
                .id("6")
                .price(60000L)
                .status("AVAILABLE_FOR_PURCHASE")
                .carDetails(carDetails6) // 초기화
                .likes(List.of(new Like(), new Like(), new Like(), new Like())) // 4 likes
                .build();

        List<MarketplaceCar> mockCars = List.of(car1, car2, car3, car4, car5, car6);

        Mockito.when(marketplaceCarRepository.findAll()).thenReturn(mockCars);

        User mockUser = User.builder()
                .id(1L)
                .build();
        Mockito.when(userResolver.getCurrentUser()).thenReturn(mockUser);

        // lenient()로 불필요한 stubbing을 무시
        Mockito.lenient().when(likeRepository.countByMarketplaceCarId(Mockito.anyString()))
                .thenAnswer(invocation -> {
                    String carId = invocation.getArgument(0);
                    switch (carId) {
                        case "1": return 2L;
                        case "2": return 1L;
                        case "3": return 3L;
                        case "4": return 0L;
                        case "5": return 2L;
                        case "6": return 4L;
                        default: return 0L;
                    }
                });
        Mockito.lenient().when(likeRepository.existsByMarketplaceCarIdAndUserId(Mockito.anyString(), Mockito.anyLong()))
                .thenReturn(true);

        // When
        List<MarketplaceCarResponse> result = marketplaceCarService.getTop5CarsByLikes();


        // Then
        assertEquals(5, result.size());
        assertEquals("6", result.get(0).id()); // 4 likes
        assertEquals("3", result.get(1).id()); // 3 likes
        assertEquals("1", result.get(2).id()); // 2 likes
        assertEquals("5", result.get(3).id()); // 2 likes
        assertEquals("2", result.get(4).id()); // 1 like
    }

    // 존재할때 차량 삭제
    @Test
    void deleteCar_whenCarExists_deletesCar() {
        // Given
        String carId = "1";
        MarketplaceCar car = MarketplaceCar.builder().id(carId).build();
        Mockito.when(marketplaceCarRepository.findById(carId)).thenReturn(Optional.of(car));

        // When
        marketplaceCarService.deleteCar(carId);

        // Then
        Mockito.verify(marketplaceCarRepository).delete(car);
    }


    // 존재하지 않을때 NotFoundException 테스트
    @Test
    void deleteCar_whenCarDoesNotExist_throwsCarNotFoundException() {
        // Given
        String carId = "1";
        Mockito.when(marketplaceCarRepository.findById(carId)).thenReturn(Optional.empty());

        // When / Then
        Assertions.assertThrows(CarNotFoundException.class, () -> marketplaceCarService.deleteCar(carId));
        Mockito.verify(marketplaceCarRepository, Mockito.never()).delete(Mockito.any());
    }

    // BaseCar를 찾았을때

    @Test
    void findBaseCar_whenCarExists_returnsBaseCar() {
        // Given
        String licensePlate = "ABC123";
        String ownerName = "John Doe";
        BaseCar baseCar = BaseCar.builder()
                .id("1")
                .carDetails(CarDetails.builder().licensePlate(licensePlate).build())
                .ownerName(ownerName)
                .build();
        Mockito.when(baseCarRepository.findByCarDetails_LicensePlateAndOwnerName(licensePlate, ownerName))
                .thenReturn(Optional.of(baseCar));

        // When
        BaseCar result = marketplaceCarService.findBaseCar(licensePlate, ownerName);

        // Then
        Assertions.assertEquals(baseCar, result);
    }


    //BaseCar를 찾지 못했을때
    @Test
    void findBaseCar_whenCarDoesNotExist_throwsBaseCarNotFoundException() {
        // Given
        String licensePlate = "ABC123";
        String ownerName = "John Doe";
        Mockito.when(baseCarRepository.findByCarDetails_LicensePlateAndOwnerName(licensePlate, ownerName))
                .thenReturn(Optional.empty());

        // When / Then
        Assertions.assertThrows(BaseCarNotFoundException.class,
                () -> marketplaceCarService.findBaseCar(licensePlate, ownerName));
    }
}
