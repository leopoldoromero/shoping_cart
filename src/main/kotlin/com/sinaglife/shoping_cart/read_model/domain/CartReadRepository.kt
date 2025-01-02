package com.sinaglife.shoping_cart.read_model.domain

import com.sinaglife.shoping_cart.shared.domain.critreria.Criteria

interface CartReadRepository {
    fun save(cart: CartReadModel)
    fun find(id: String): CartReadModel?
    fun findByCriteria(criteria: Criteria?): MutableList<CartReadModel>
    fun updateOne(cart: CartReadModel)
    fun deleteCart(id: String)
}