package com.sinaglife.shoping_cart.shared.infrastructure.logger
import com.sinaglife.shoping_cart.shared.domain.AppLogger
import com.sinaglife.shoping_cart.shared.domain.LogInput
import org.slf4j.LoggerFactory
import java.time.LocalDateTime

class ConsoleSpringLogger(private val context: String) : AppLogger {
    private val logger = LoggerFactory.getLogger(ConsoleSpringLogger::class.java)

    override fun log(input: LogInput) {
        logger.info(formatMessage("INFO", input))
    }

    override fun error(input: LogInput) {
        logger.error(formatMessage("ERROR", input))
    }

    override fun warn(input: LogInput) {
        logger.warn(formatMessage("WARN", input))
    }

    override fun debug(input: LogInput) {
        logger.debug(formatMessage("DEBUG", input))
    }

    private fun formatMessage(level: String, input: LogInput): String {
        return "${LocalDateTime.now()} [$level] [${input.methodName}] " +
                "Input: ${input?.input ?: "NONE"}, Output: ${input?.output ?: "NONE"}, Message: ${input?.message ?: "NONE"}\n"
    }
}