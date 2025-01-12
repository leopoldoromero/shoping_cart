package com.sinaglife.shoping_cart.shared.infrastructure.logger

import com.sinaglife.shoping_cart.shared.domain.AppLogger
import com.sinaglife.shoping_cart.shared.domain.LogInput

class LoggerWrapper(private val context: String) : AppLogger {

    private val environment: String = System.getenv("APP_ENVIRONMENT") ?: "DEV"
    private val logger: AppLogger = if (environment == "DEV") {
        ConsoleSpringLogger(context)
    } else {
        FileLogger(context)
    }

    override fun log(input: LogInput) {
        logger.log(input)
    }

    override fun error(input: LogInput) {
        logger.error(input)
    }

    override fun warn(input: LogInput) {
        logger.warn(input)
    }

    override fun debug(input: LogInput) {
        logger.debug(input)
    }
}
