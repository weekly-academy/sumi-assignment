package com.mentoring.assignment.membership.global.config;



import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;




@Configuration
public class SwaggerConfig {

    // Docket : swagger 설정의 핵심이 되는 Bean
    @Bean
    public OpenAPI api() {
        return new OpenAPI()
                .info(apiInfo())
                .components(new Components());

    }

    private Info apiInfo() {
        return new Info()
                .title("MemberShip Service REST API Documentation")
                .description("멤버쉽 서비스 api 문서")
                .version("1.0.0");
    }
}