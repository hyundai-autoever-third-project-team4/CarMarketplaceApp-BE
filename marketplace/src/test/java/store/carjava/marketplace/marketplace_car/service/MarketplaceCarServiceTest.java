package store.carjava.marketplace.marketplace_car.service;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import store.carjava.marketplace.app.marketplace_car.repository.MarketplaceCarRepository;
import store.carjava.marketplace.app.marketplace_car.service.MarketplaceCarService;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class MarketplaceCarServiceTest {


    @InjectMocks
    private MarketplaceCarService marketplaceCarService;

    @Mock
    private MarketplaceCarRepository marketplaceCarRepository;

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

        assertThat(marketplaceCarService.isValidStatus(nullStatus)).isFalse();
        assertThat(marketplaceCarService.isValidStatus(emptyStatus)).isFalse();
    }
}
