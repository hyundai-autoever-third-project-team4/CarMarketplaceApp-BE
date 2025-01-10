//package store.carjava.marketplace.common.security;
//
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.oauth2.core.oidc.user.OidcUser;
//import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
//import org.springframework.stereotype.Component;
//import store.carjava.marketplace.app.user.entity.User;
//import store.carjava.marketplace.app.user.repository.UserRepository;
//import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
//
//import java.io.IOException;
//import java.util.List;
//
//import org.springframework.security.core.context.SecurityContextHolder;
//
//@Component
//public class CustomOAuth2SuccessHandler implements AuthenticationSuccessHandler {
//
//    private final UserRepository userRepository;
//
//    public CustomOAuth2SuccessHandler(UserRepository userRepository) {
//        this.userRepository = userRepository;
//    }
//
//    @Override
//    public void onAuthenticationSuccess(HttpServletRequest request,
//                                        HttpServletResponse response,
//                                        Authentication authentication) throws IOException, ServletException {
//        OAuth2AuthenticationToken authToken = (OAuth2AuthenticationToken) authentication;
//        OidcUser oidcUser = (OidcUser) authToken.getPrincipal();
//
//        String email = oidcUser.getAttribute("email");
//        List<String> groups = oidcUser.getAttribute("groups");
//
//        String role = groups.stream()
//                .filter(group -> group.startsWith("ROLE_"))
//                .findFirst()
//                .orElse("ROLE_USER");
//
//        // DB에 사용자 저장 또는 업데이트
//        User user = userRepository.findByEmail(email)
//                .orElseGet(() -> {
//                    // 새로운 사용자 생성
//                    User newUser = User.builder()
//                            .email(email)
//                            .role(role)
//                            .build();
//                    // 새로운 사용자 저장
//                    return userRepository.save(newUser);
//                });
//
//
//        if (!user.getRole().equals(role)) {
//            userRepository.save(user.updateRole(role));
//        }
//
//        // Spring Security 권한 설정
//        List<SimpleGrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(role));
//
//        Authentication updatedAuth = new OAuth2AuthenticationToken(
//                oidcUser,
//                authorities,
//                authToken.getAuthorizedClientRegistrationId()
//        );
//
//        // SecurityContext에 새로운 Authentication 설정
//        SecurityContextHolder.getContext().setAuthentication(updatedAuth);
//
//        // 인증 성공 후 리다이렉션
//        response.sendRedirect("/");
//    }
//
//}
//
//
