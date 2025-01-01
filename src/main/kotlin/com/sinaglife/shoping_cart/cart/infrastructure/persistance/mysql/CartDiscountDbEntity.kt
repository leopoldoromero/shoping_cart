package com.sinaglife.shoping_cart.cart.infrastructure.persistance.mysql

import com.sinaglife.shoping_cart.cart.domain.cart_discount.CartDiscount
import jakarta.persistence.*

@Entity
@Table(name = "cart_discount")
class CartDiscountDbEntity(
    @Id
    @Column(name = "id")
    val id: String,

    @Column(name = "amount")
    val amount: Int,

    @Column(name = "type")
    val type: String,

    @Column(name = "individualUse")
    val individualUse: Boolean,

    @Column(name = "code")
    val code: String,

    @Column(name = "cartId")
    val cartId: String,
    ) {
    companion object {
        fun fromDomainEntity(discount: CartDiscount, cartId: String): CartDiscountDbEntity {
            return CartDiscountDbEntity(
                id = discount.id.toString(),
                amount = discount.amount.value,
                type = discount.type.value.toString(),
                individualUse = discount.individualUse,
                code = discount.code.value,
                cartId = cartId,
            )
        }
    }
}