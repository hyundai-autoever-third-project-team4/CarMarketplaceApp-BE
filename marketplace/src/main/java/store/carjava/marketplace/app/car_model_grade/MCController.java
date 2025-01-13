package store.carjava.marketplace.app.car_model_grade;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MCController {
    private final MCService marketplaceCarService;

    @GetMapping("/recommand")
    public ResponseEntity<?> getCarRecommandation(
            @RequestBody MarketplaceCarRecommandRequest request
    ) {
        marketplaceCarService.getRecommand(request);
        //MarketplaceCarRecommandListResponse response = marketplaceCarService.getRecommand(request);

        //return ResponseEntity.ok(response);
        return ResponseEntity.ok(200);
    }
}
