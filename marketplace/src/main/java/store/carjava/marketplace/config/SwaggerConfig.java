package store.carjava.marketplace.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {

    private static final String TITLE = "차자바 API 명세";
    private static final String VERSION = "0.1";
    private static final String DESCRIPTION = "중고차 프로젝트 백엔드 API 명세서입니다.";

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title(TITLE)
                        .version(VERSION)
                        .description(DESCRIPTION))
                .servers(List.of(new Server().url("")));    // 배포 url 입력
    }
}