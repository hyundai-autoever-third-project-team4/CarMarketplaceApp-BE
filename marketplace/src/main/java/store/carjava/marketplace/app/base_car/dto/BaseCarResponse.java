package store.carjava.marketplace.app.base_car.dto;


import lombok.Builder;
import store.carjava.marketplace.app.vo.CarDetails;

@Builder
public record BaseCarResponse(

        String id,
        String ownerName,
        CarDetails carDetails,
        String mainImage

        ) {


}
