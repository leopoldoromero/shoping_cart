package com.sinaglife.shoping_cart.read_model.infrastructure.persistence.mongodb

import com.sinaglife.shoping_cart.read_model.domain.CartItemReadModel
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime

@Document(collection = "cart")
class CartReadModelDbDocument(
    val id: String,
    val items: List<CartItemReadModel>,
    val subTotal: Double,
    val total: Double,
    val discount: Double?,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
    val customerId: String?
) {
}
