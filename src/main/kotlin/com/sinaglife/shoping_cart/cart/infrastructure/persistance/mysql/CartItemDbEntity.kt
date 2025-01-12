package com.sinaglife.shoping_cart.cart.infrastructure.persistance.mysql

import com.sinaglife.shoping_cart.cart.domain.cart_item.CartItem
import jakarta.persistence.*

@Entity
@Table(name = "cart_items")
class CartItemDbEntity(
    @Id
    @Column(name = "id")
    val id: String,

    @Column(name = "quantity")
    val quantity: Int,

    @Column(name = "price")
    val price: Double,

    @Column(name = "cartId")
    val cartId: String,
) {
    companion object {
        fun fromDomainEntity(item: CartItem, cartId: String): CartItemDbEntity {
            return CartItemDbEntity(
                id = item.id.value.toString(),
                quantity = item.quantity.value,
                price = item.price.value,
                cartId = cartId,
            )
        }
    }
}
