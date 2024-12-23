package com.sinaglife.shoping_cart.cart.application

import com.sinaglife.shoping_cart.cart.domain.CartRepository
import com.sinaglife.shoping_cart.cart.domain.cart_item.CartItemPrimitives
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service

@Service
class CartCreator(
    @Qualifier("CartMysqlRepository")
    private val repository: CartRepository,
) {
    fun execute(
        id: String,
        items: List<CartItemPrimitives>,
        customerId: String?,
    ) {
        println("${id}, items: ${items.toString()} for customer: ${customerId}")
    }
}