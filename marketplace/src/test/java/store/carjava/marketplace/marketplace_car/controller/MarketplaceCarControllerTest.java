//package store.carjava.marketplace.marketplace_car.controller;
//
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mockito;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//import store.carjava.marketplace.app.marketplace_car.controller.MarketplaceCarController;
//import store.carjava.marketplace.app.marketplace_car.dto.MarketplaceCarRegisterRequest;
//import store.carjava.marketplace.app.marketplace_car.dto.MarketplaceCarResponse;
//import store.carjava.marketplace.app.marketplace_car.service.MarketplaceCarService;
//import org.springframework.boot.test.mock.mockito.*;
//import static org.springframework.http.RequestEntity.post;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//import static org.hamcrest.Matchers.*;
//
//import java.time.LocalDate;
//import java.util.Arrays;
//import java.util.List;
//
//@SpringMockBeans({
//        @SpringMockBean(MarketplaceCarService.class)
//})
//@WebMvcTest(MarketplaceCarController.class)
//public class MarketplaceCarControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Autowired
//    private ObjectMapper objectMapper;
//
//    String jwtToken = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxIiwiaXNzIjoiaHR0cHM6Ly9jaGFqYXZhLnN0b3JlIiwiYXVkIjoiYWNjb3VudCIsImlhdCI6MTczNzI1NDkzMywiZXhwIjoxNzM3MjU4NTMzLCJlbWFpbCI6ImRucmFsczc5MjlAbmF2ZXIuY29tIiwicm9sZSI6IlJPTEVfQURNSU4iLCJ0eXAiOiJCZWFyZXIifQ.MbldsMvbJ1IQpjHkRuHuHGpmMD3xi-Cq8oXxKNmuZyI";
//
////    @Test
////    void getAllCar_shouldReturnCarList() throws Exception {
////        // Mock 데이터 준비
////        List<MarketplaceCarResponse> mockCars = Arrays.asList(
////                MarketplaceCarResponse.builder()
////                        .id("GGI241223011164")
////                        .mileage(22103)
////                        .name("쏘나타")
////                        .price(43900000L)
////                        .marketplaceRegistrationDate(LocalDate.of(2023, 1, 1))
////                        .mainImage("https://example.com/images/car-main.jpg")
////                        .islike(true)
////                        .like(123L)
////                        .build(),
////                MarketplaceCarResponse.builder()
////                        .id("GGI241223011165")
////                        .mileage(15000)
////                        .name("아반떼")
////                        .price(25000000L)
////                        .marketplaceRegistrationDate(LocalDate.of(2023, 5, 10))
////                        .mainImage("https://example.com/images/car-main2.jpg")
////                        .islike(false)
////                        .like(95L)
////                        .build()
////        );
////
////        // Service의 Mock 설정
////        when(marketplaceCarService.getAllCars()).thenReturn(mockCars);
////
////        // API 호출 및 검증
////        mockMvc.perform(get("/cars").header("Authorization", "Bearer " + jwtToken))
////                .andExpect(status().isOk())
////                .andExpect(jsonPath("$.length()", is(2)))
////                .andExpect(jsonPath("$[0].id", is("GGI241223011164")))
////                .andExpect(jsonPath("$[0].mileage", is(22103)))
////                .andExpect(jsonPath("$[0].name", is("쏘나타")))
////                .andExpect(jsonPath("$[0].price", is(43900000)))
////                .andExpect(jsonPath("$[0].marketplaceRegistrationDate", is("2023-01-01")))
////                .andExpect(jsonPath("$[0].mainImage", is("https://example.com/images/car-main.jpg")))
////                .andExpect(jsonPath("$[0].islike", is(true)))
////                .andExpect(jsonPath("$[0].like", is(123)))
////                .andExpect(jsonPath("$[1].id", is("GGI241223011165")))
////                .andExpect(jsonPath("$[1].mileage", is(15000)))
////                .andExpect(jsonPath("$[1].name", is("아반떼")))
////                .andExpect(jsonPath("$[1].price", is(25000000)))
////                .andExpect(jsonPath("$[1].marketplaceRegistrationDate", is("2023-05-10")))
////                .andExpect(jsonPath("$[1].mainImage", is("https://example.com/images/car-main2.jpg")))
////                .andExpect(jsonPath("$[1].islike", is(false)))
////                .andExpect(jsonPath("$[1].like", is(95)));
////    }
//
//
//    @Test
//    void registerCar_success() throws Exception {
//        // Given
//        MarketplaceCarRegisterRequest request = new MarketplaceCarRegisterRequest("1234-AB", "John Doe");
//        Mockito.doNothing().when(marketplaceCarService).sellRegisterCar(request);
//        String jsonContent = objectMapper.writeValueAsString(request);
//        // When & Then
//        mockMvc.perform(post("/car/register")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(jsonContent))
//                .andExpect(status().isOk())
//                .andExpect(content().string("판매차량 등록 성공!"));
//
//        // 서비스 호출 여부 검증
//        Mockito.verify(marketplaceCarService).sellRegisterCar(request);
//    }
//}
