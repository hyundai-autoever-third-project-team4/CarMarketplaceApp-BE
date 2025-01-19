package store.carjava.marketplace.review.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import store.carjava.marketplace.app.marketplace_car.entity.MarketplaceCar;
import store.carjava.marketplace.app.marketplace_car.repository.MarketplaceCarRepository;
import store.carjava.marketplace.app.review.dto.ReviewCreateRequest;
import store.carjava.marketplace.app.review.dto.ReviewCreateResponse;
import store.carjava.marketplace.app.review.entity.Review;
import store.carjava.marketplace.app.review.exception.ReviewAlreadyExistsException;
import store.carjava.marketplace.app.review.exception.ReviewIdNotFoundException;
import store.carjava.marketplace.app.review.exception.ReviewWriterNotMatchException;
import store.carjava.marketplace.app.review.repository.ReviewRepository;
import store.carjava.marketplace.app.review.service.ReviewService;
import store.carjava.marketplace.app.user.entity.User;
import store.carjava.marketplace.app.user.repository.UserRepository;
import store.carjava.marketplace.common.util.user.UserResolver;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ReviewServiceTest {

    @InjectMocks
    private ReviewService reviewService;

    @Mock
    private ReviewRepository reviewRepository;

    @Mock
    private MarketplaceCarRepository marketplaceCarRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserResolver userResolver;

    private User mockUser;
    private MarketplaceCar mockCar;

    void setUp() {
        // Mock 데이터 설정
        mockUser = User.builder()
                .id(1L)           // 예시로 사용자 ID 설정
                .email("dnrals7929@naver.com") // 예시로 이메일 설정
                .role("USER")     // 예시로 역할 설정
                .name("Test User") // 예시로 이름 설정
                .phone("010-1234-5678") // 예시로 전화번호 설정
                .address("123 Test Street") // 예시로 주소 설정
                .build();

        mockCar = MarketplaceCar.builder()
                .id("GGI241223011164")     // 예시로 차량 ID 설정
                .status("AVAILABLE_FOR_PURCHASE") // 예시로 차량 상태 설정
                .build();
    }

    @Test
    void testCreateReview_Success() {
        // Given
        ReviewCreateRequest request = new ReviewCreateRequest("Excellent car!", "아주 좋습니다!", 5.0);
        when(userResolver.getCurrentUser()).thenReturn(mockUser);
        when(marketplaceCarRepository.findById("GGI241223011164")).thenReturn(Optional.of(mockCar));
        when(reviewRepository.existsByUserAndMarketplaceCar(mockUser, mockCar)).thenReturn(false);

        // When
        ReviewCreateResponse response = reviewService.createReview(request, List.of());

        // Then
        assertNotNull(response);
        assertEquals("Excellent car!", response.content());
        assertEquals(5, response.starRate());
        verify(reviewRepository, times(1)).save(any(Review.class));
    }

    @Test
    void testCreateReview_ReviewAlreadyExists() {
        // Given
        ReviewCreateRequest request = new ReviewCreateRequest("Excellent car!", "아주 좋습니다!", 5.0);
        when(userResolver.getCurrentUser()).thenReturn(mockUser);
        when(marketplaceCarRepository.findById("GGI241223011164")).thenReturn(Optional.of(mockCar));
        when(reviewRepository.existsByUserAndMarketplaceCar(mockUser, mockCar)).thenReturn(true);

        // When & Then
        assertThrows(ReviewAlreadyExistsException.class, () -> reviewService.createReview(request, List.of()));
    }

    @Test
    void testGetMyReviews() {
        // Given
        when(userResolver.getCurrentUser()).thenReturn(mockUser);
        when(reviewRepository.findAllByUserIdOrderByCreatedAtDesc(mockUser.getId()))
                .thenReturn(List.of(new Review()));  // Mock review 리스트

        // When
        var result = reviewService.getMyReviews();

        // Then
        assertNotNull(result);
        assertTrue(result.reviewInfoList().size() > 0);
    }

    @Test
    void testDeleteReview_Success() {
        // Given
        mockUser = User.builder()
                .id(1L)
                .email("testuser@example.com")
                .role("USER")
                .name("Test User")
                .phone("010-1234-5678")
                .address("123 Test Street")
                .build();

        mockCar = MarketplaceCar.builder()
                .id("GGI241223011164")
                .status("AVAILABLE")
                .build();

        // 리뷰 객체 생성
        Review mockReview = Review.builder()
                .id(1L) // 리뷰 ID 설정
                .marketplaceCar(mockCar) // 차량 연결
                .user(mockUser) // 사용자 연결
                .content("Great car!") // 리뷰 내용 설정
                .starRate(5.0) // 평점 설정
                .createdAt(LocalDateTime.now()) // 생성일 설정
                .build();

        // Mock 설정
        when(userResolver.getCurrentUser()).thenReturn(mockUser);
        when(reviewRepository.findById(1L)).thenReturn(Optional.of(mockReview));

        // When
        var response = reviewService.deleteReview(1L);

        // Then
        assertNotNull(response);  // 응답이 null이 아니어야 함
        verify(reviewRepository, times(1)).delete(mockReview);  // 리뷰 삭제가 정확히 한 번 호출되었는지 확인
    }

    @Test
    void testDeleteReview_ReviewNotFound() {
        // Given
        when(reviewRepository.findById(1L)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(ReviewIdNotFoundException.class, () -> reviewService.deleteReview(1L));
    }

    @Test
    void testDeleteReview_WriterNotMatch() {
        // Given
        // 다른 사용자 생성
        User differentUser = User.builder()
                .id(2L) // 다른 사용자 ID 설정
                .email("differentuser@example.com")
                .role("USER")
                .name("Different User")
                .phone("010-9876-5432")
                .address("456 Another Street")
                .build();

        // mockUser 생성
        mockUser = User.builder()
                .id(1L)
                .email("testuser@example.com")
                .role("USER")
                .name("Test User")
                .phone("010-1234-5678")
                .address("123 Test Street")
                .build();

        // 리뷰 객체 생성 (작성자와 요청자가 다름)
        Review mockReview = Review.builder()
                .id(1L)
                .marketplaceCar(mockCar) // 이미 설정된 mockCar 객체 사용
                .user(differentUser) // 다른 사용자 설정
                .content("Great car!")
                .starRate(5.0)
                .createdAt(LocalDateTime.now())
                .build();

        // Mock 설정
        when(userResolver.getCurrentUser()).thenReturn(mockUser);  // 현재 사용자 mockUser
        when(reviewRepository.findById(1L)).thenReturn(Optional.of(mockReview));  // 리뷰 조회

        // When & Then
        // 다른 사용자일 경우 ReviewWriterNotMatchException 예외가 발생해야 함
        assertThrows(ReviewWriterNotMatchException.class, () -> reviewService.deleteReview(1L));
    }

}
