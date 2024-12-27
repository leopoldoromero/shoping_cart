package com.sinaglife.shoping_cart.cart.infrastructure.persistance.mongodb

import com.sinaglife.shoping_cart.cart.domain.Cart
import com.sinaglife.shoping_cart.cart.domain.CartId
import com.sinaglife.shoping_cart.cart.domain.CartPrimitives
import com.sinaglife.shoping_cart.cart.domain.CartRepository
import com.sinaglife.shoping_cart.shared.domain.critreria.Criteria
import org.springframework.stereotype.Repository

@Repository(value = "CartMongoRepository")
class CartMongoRepository(private val client: CartMongoClientRepository) : CartRepository {
    override fun save(cart: Cart) {
        val primitives = cart.toPrimitives()
        client.save(
            CartDbSchema(
                id = primitives.id,
                items = primitives.items,
                discount = primitives.discount,
                createdAt = primitives.createdAt,
                updatedAt = primitives.updatedAt,
                customerId = primitives.customerId,
            )
        )
    }

    override fun find(id: CartId): Cart? {
        return client.findById(id.value.toString()).map {
            dbSchema ->
            Cart.fromPrimitives(
                CartPrimitives(
                    id = dbSchema.id,
                    items = dbSchema.items.toMutableList(),
                    discount = dbSchema.discount,
                    createdAt = dbSchema.createdAt,
                    updatedAt = dbSchema.updatedAt,
                    customerId = dbSchema.customerId,
                )
            )
        }.orElse(null)
    }

    override fun findByCriteria(criteria: Criteria?): MutableList<Cart> {
        TODO("Not yet implemented")
    }

    override fun updateOne(cart: Cart) {
        TODO("Not yet implemented")
    }
}