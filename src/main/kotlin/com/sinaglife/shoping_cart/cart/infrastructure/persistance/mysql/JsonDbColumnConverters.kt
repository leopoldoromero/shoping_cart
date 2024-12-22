package com.sinaglife.shoping_cart.cart.infrastructure.persistance.mysql

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.sinaglife.shoping_cart.cart.domain.cart_item.CartItemImgPrimitives
import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter

class JsonDbColumnConverters {
}

@Converter(autoApply = true)
class CartItemImgPrimitivesConverter : AttributeConverter<CartItemImgPrimitives, String> {

    private val objectMapper = jacksonObjectMapper()

    override fun convertToDatabaseColumn(attribute: CartItemImgPrimitives?): String? {
        return attribute?.let { objectMapper.writeValueAsString(it) }
    }

    override fun convertToEntityAttribute(dbData: String?): CartItemImgPrimitives? {
        return dbData?.let { objectMapper.readValue<CartItemImgPrimitives>(it) } as CartItemImgPrimitives
    }
}