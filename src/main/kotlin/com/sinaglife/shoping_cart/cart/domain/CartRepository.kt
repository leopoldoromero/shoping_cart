package com.sinaglife.shoping_cart.cart.domain

import com.sinaglife.shoping_cart.shared.domain.critreria.Criteria

interface CartRepository {
    fun save(cart: Cart)
    fun find(id: CartId): Cart?
    fun findByCriteria(criteria: Criteria?): MutableList<Cart>
    fun updateOne(cart: Cart)
    fun deleteCart(cartId: CartId)
}