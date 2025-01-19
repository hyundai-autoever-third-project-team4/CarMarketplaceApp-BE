//package store.carjava.marketplace.review.controller;
//
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.test.web.servlet.MockMvc;
//import store.carjava.marketplace.app.review.controller.ReviewController;
//import store.carjava.marketplace.app.review.dto.ReviewInfoDto;
//import store.carjava.marketplace.app.review.service.ReviewService;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import java.time.LocalDateTime;
//import java.util.List;
//
//import static org.mockito.Mockito.when;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@WebMvcTest(ReviewController.class)
//class ReviewControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @MockBean
//    private ReviewService reviewService;
//
//    @Test
//    void testGetReviewDetail() throws Exception {
//        // Given
//        Long reviewId = 1L;
//        ReviewInfoDto mockReviewInfoDto = new ReviewInfoDto(
//                1L,                             // reviewId
//                5.0,                            // starRate
//                "test review",                  // content
//                LocalDateTime.now(),            // createdAt
//                "2023 GV70 가솔린 2.5 터보 AWD", // carName
//                "car123",                       // carId
//                "GV70",                         // carModel
//                "abc@naver.com",                // writerEmail
//                List.of()                        // images (빈 리스트로 초기화, 실제 이미지 데이터가 있다면 여기에 추가)
//        );
//        when(reviewService.getReviewDetail(reviewId)).thenReturn(mockReviewInfoDto);
//
//        // When & Then
//        mockMvc.perform(get("/review/{reviewId}", reviewId))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.content").value("test review"))
//                .andExpect(jsonPath("$.starRate").value(5));
//    }
//}