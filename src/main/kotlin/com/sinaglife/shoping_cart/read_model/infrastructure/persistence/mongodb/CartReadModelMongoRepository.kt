package com.sinaglife.shoping_cart.read_model.infrastructure.persistence.mongodb

import com.sinaglife.shoping_cart.read_model.domain.CartReadModel
import com.sinaglife.shoping_cart.read_model.domain.CartReadRepository
import com.sinaglife.shoping_cart.shared.domain.critreria.Criteria
import org.springframework.stereotype.Repository

@Repository(value = "CartReadModelMongoRepository")
class CartReadModelMongoRepository(private val client: CartReadModelMongoClientRepository) : CartReadRepository {
    override fun save(cart: CartReadModel) {
        client.save(
            CartReadModelDbDocument(
                id = cart.id,
                items = cart.items,
                subTotal = cart.subTotal,
                total = cart.total,
                discount = cart.discount,
                createdAt = cart.createdAt,
                updatedAt = cart.updatedAt,
                customerId = cart.customerId,
            )
        )
    }

    override fun find(id: String): CartReadModel? {
        return client.findById(id).map {
            dbSchema ->
            CartReadModel(
                id = dbSchema.id,
                items = dbSchema.items.toMutableList(),
                subTotal = dbSchema.subTotal,
                total = dbSchema.total,
                discount = dbSchema.discount,
                createdAt = dbSchema.createdAt,
                updatedAt = dbSchema.updatedAt,
                customerId = dbSchema.customerId,
            )
        }.orElse(null)
    }

    override fun findByCriteria(criteria: Criteria?): MutableList<CartReadModel> {
        TODO("Not yet implemented")
    }

    override fun updateOne(cart: CartReadModel) {
        TODO("Not yet implemented")
    }

    override fun deleteCart(id: String) {
        client.deleteById(id)
    }
}