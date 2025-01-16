//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//@EnableWebSocketMessageBroker
//public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
//
//    @Override
//    public void configureMessageBroker(MessageBrokerRegistry config) {
//        // 클라이언트가 수신할 경로 설정
//        config.enableSimpleBroker("/topic", "/queue");
//        // 서버가 메시지를 받을 API 경로 설정
//        config.setApplicationDestinationPrefixes("/app");
//    }
//
//    @Override
//    public void registerStompEndpoints(StompEndpointRegistry registry) {
//        // 클라이언트가 연결할 엔드포인트 설정
//        registry.addEndpoint("/ws").setAllowedOrigins("*").withSockJS();
//    }
//}