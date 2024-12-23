package com.sinaglife.shoping_cart.cart.domain

import com.sinaglife.shoping_cart.shared.domain.critreria.Criteria

interface CartRepository {
    fun updateOne(cart: Cart)
    fun findByCriteria(criteria: Criteria?): MutableList<Cart>
    fun save(cart: Cart)
}