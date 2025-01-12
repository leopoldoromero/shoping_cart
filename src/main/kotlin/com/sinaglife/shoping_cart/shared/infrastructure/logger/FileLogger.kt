package com.sinaglife.shoping_cart.shared.infrastructure.logger

import com.sinaglife.shoping_cart.shared.domain.AppLogger
import com.sinaglife.shoping_cart.shared.domain.LogInput
import org.slf4j.LoggerFactory
import java.nio.file.Files
import java.nio.file.Paths
import java.time.LocalDateTime

class FileLogger(private val context: String) : AppLogger {
    private val logFilePath: String = "/var/logs/app.log"

    init {
        Files.createDirectories(Paths.get(logFilePath).parent)
    }

    private val logger = LoggerFactory.getLogger(FileLogger::class.java)

    private fun writeToFile(input: LogInput, level: String) {
        val logMessage = "${LocalDateTime.now()} [$level] [${input.methodName}] " +
                "Input: ${input?.input ?: "NONE"}, Output: ${input?.output ?: "NONE"}, Message: ${input?.message ?: "NONE"}\n"
        Files.write(Paths.get(logFilePath), logMessage.toByteArray(), java.nio.file.StandardOpenOption.CREATE, java.nio.file.StandardOpenOption.APPEND)
    }

    override fun log(input: LogInput) {
        writeToFile(input, "INFO")
    }

    override fun error(input: LogInput) {
        writeToFile(input, "ERROR")
    }

    override fun warn(input: LogInput) {
        writeToFile(input, "WARN")
    }

    override fun debug(input: LogInput) {
        writeToFile(input, "DEBUG")
    }
}
