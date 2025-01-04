package com.sinaglife.shoping_cart.shared.config

import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.security.SecurityRequirement
import io.swagger.v3.oas.models.security.SecurityScheme
import org.springdoc.core.models.GroupedOpenApi
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SwaggerConfig {

    @Bean
    fun customOpenAPI(): OpenAPI {
        val basicAuthScheme = SecurityScheme()
            .type(SecurityScheme.Type.HTTP)
            .scheme("basic")
        return OpenAPI()
            .info(
                Info()
                    .title("Shopping cart API")
                    .description("This is the shopping cart micro service for sinaglife")
                    .version("2.0.0")
            )
            .components(
                io.swagger.v3.oas.models.Components().addSecuritySchemes("basicAuth", basicAuthScheme)
            )
            .addSecurityItem(SecurityRequirement().addList("basicAuth"))
    }

    @Bean
    fun publicApi(): GroupedOpenApi {
        return GroupedOpenApi.builder()
            .group("public-api")
            .pathsToMatch("/api/**")
            .build()
    }

}