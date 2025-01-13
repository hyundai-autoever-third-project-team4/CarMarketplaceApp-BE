package store.carjava.marketplace.app.marketplace_car_option.dto;


import lombok.Builder;

@Builder
public record marketplaceCarOptionInfoDto(

        Long id,
        String marketplaceCarId,
        Long optionId,
        String optionName,
        String optionDescription,
        String optionImg,
        Boolean isPresent

) {

}