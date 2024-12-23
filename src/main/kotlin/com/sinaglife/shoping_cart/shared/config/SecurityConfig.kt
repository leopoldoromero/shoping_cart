package com.sinaglife.shoping_cart.shared.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer
import org.springframework.security.config.annotation.web.configurers.CorsConfigurer
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource

@Configuration
@EnableWebSecurity
class SecurityConfig {
    @Bean
    @Primary
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .authorizeHttpRequests { authorize: AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry ->
                authorize
                    // TODO: allow all request while the security strategy its done
                    .requestMatchers("/api/v1/**").permitAll()
                    .anyRequest().permitAll()
            }
//            .authenticationManager { auth ->
//                val user = (auth.principal as UserDetails)
//                UsernamePasswordAuthenticationToken(user, "", user.authorities)
//            }
            .cors { it: CorsConfigurer<HttpSecurity> ->
                it.configurationSource(corsConfigurationSource())

            }
            .sessionManagement { session ->
                session.sessionCreationPolicy(SessionCreationPolicy.ALWAYS)
            }
            .headers { header ->
                header.xssProtection { xss -> xss.disable() }
                header.frameOptions { frameOption -> frameOption.disable() }
            }
            .csrf { it -> it.disable() }

        return http.build()
    }

    @Bean
    fun corsConfigurationSource(): CorsConfigurationSource {
        val configuration = CorsConfiguration()
        configuration.allowedOrigins = listOf("http://localhost:3000", "http://localhost:8080")
        configuration.allowedMethods = listOf("GET", "POST", "PUT", "DELETE")
        configuration.allowedHeaders = listOf("authorization", "content-type")
        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", configuration)
        return source
    }
}