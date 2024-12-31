package com.sinaglife.shoping_cart.cart.infrastructure.persistance.mysql

import com.sinaglife.shoping_cart.cart.domain.cart_item.CartItem
import jakarta.persistence.*

@Entity
@Table(name = "cart_item")
class CartItemDbEntity(
    @Id
    @Column(name = "id")
    val id: String,

    @Column(name = "quantity")
    val quantity: Int,

    @Column(name = "price")
    val price: Int,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id")
    var cart: CartDbEntity? = null
) {
    companion object {
        fun fromDomainEntity(item: CartItem): CartItemDbEntity {
            return CartItemDbEntity(
                id = item.id.value.toString(),
                quantity = item.quantity.value,
                price = item.price.value,
            )
        }
    }
}
