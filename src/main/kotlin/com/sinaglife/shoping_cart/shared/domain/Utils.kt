package com.sinaglife.shoping_cart.shared.domain

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import java.io.IOException
import java.io.Serializable
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


object Utils {

    fun dateToString(dateTime: LocalDateTime): String {
        return dateTime.format(DateTimeFormatter.ISO_LOCAL_DATE)
    }

    fun jsonEncode(map: Map<String, Serializable>): String {
        return try {
            jacksonObjectMapper().writeValueAsString(map)
        } catch (e: IOException) {
            ""
        }
    }


    fun jsonDecode(body: String): Map<String, Serializable>? {
        return try {
            val mapper = jacksonObjectMapper()
            return mapper.reader().readValue(body)
        } catch (e: IOException) {
            null
        }
    }

    fun toSnake(text: String): String {
        return text.split("(?=[A-Z])".toRegex())
            .joinToString(separator = "_")
            .lowercase()
    }

    private fun toCamel(text: String): String {
        return text.split("_")
            .joinToString("") { it.capitalize() }
    }

    fun toCamelFirstLower(text: String): String {
        val camelCase = toCamel(text)
        return camelCase.substring(0, 1).lowercase() + camelCase.substring(1)
    }
}