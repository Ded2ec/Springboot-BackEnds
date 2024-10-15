package com.itgenius.springboot_store_api.config

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType
import io.swagger.v3.oas.annotations.security.SecurityScheme
import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.security.SecurityRequirement
import io.swagger.v3.oas.models.tags.Tag
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
@SecurityScheme(
    name = "bearerAuth",
    type = SecuritySchemeType.HTTP,
    bearerFormat = "JWT",
    scheme = "bearer"
)
class OpenApiConfig {

    @Bean
    fun customOpenAPI(): OpenAPI {
        return OpenAPI()
            .info(
                Info()
                    .title("Store API with Spring Boot and PostgreSQL")
                    .version("1.0")
                    .description("This is Store API using Spring Boot and PostgreSQL")
            )
            // Add Security JWT
            .addSecurityItem(SecurityRequirement().addList("bearerAuth"))
            .components(
                Components()
                    .addSecuritySchemes(
                        "bearerAuth",
                        io.swagger.v3.oas.models.security.SecurityScheme()
                            .name("bearerAuth")
                            .type(io.swagger.v3.oas.models.security.SecurityScheme.Type.HTTP)
                            .scheme("bearer")
                            .bearerFormat("JWT")
                    )
            )
            .tags(
                listOf(
                    Tag().name("Authenticate").description("Authenticate APIs"),
                    Tag().name("Categories").description("Category management APIs"),
                    Tag().name("Products").description("Product management APIs")
                )
            )
    }

}