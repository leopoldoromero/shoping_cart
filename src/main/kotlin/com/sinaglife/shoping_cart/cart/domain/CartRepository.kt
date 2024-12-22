package com.sinaglife.shoping_cart.cart.domain

import com.sinaglife.shoping_cart.shared.domain.critreria.Criteria

interface CartRepository {
    fun updateOne(cart: Cart)
    fun findByCustomerId(customerId: CartCustomerId): Cart?
    fun findMany(criteria: Criteria?): MutableList<Cart>
}