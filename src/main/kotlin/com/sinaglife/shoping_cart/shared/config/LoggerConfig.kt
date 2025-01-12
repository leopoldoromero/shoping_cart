package com.sinaglife.shoping_cart.shared.config

import com.sinaglife.shoping_cart.shared.infrastructure.logger.LoggerWrapper
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class LoggerConfig {

    @Bean
    fun loggerWrapper(): (Class<*>) -> LoggerWrapper {
        return { clazz: Class<*> ->
            LoggerWrapper(context = clazz.simpleName)
        }
    }
}
