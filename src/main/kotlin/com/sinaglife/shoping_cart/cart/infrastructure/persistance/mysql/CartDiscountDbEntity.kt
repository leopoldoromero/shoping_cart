package com.sinaglife.shoping_cart.cart.infrastructure.persistance.mysql

import com.sinaglife.shoping_cart.cart.domain.discount.Discount
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

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id")
    val cart: CartDbEntity? = null
) {
    companion object {
        fun fromDomainEntity(discount: Discount): CartDiscountDbEntity {
            return CartDiscountDbEntity(
                id = discount.id.toString(),
                amount = discount.amount.value,
                type = discount.type.value.toString(),
                individualUse = discount.individualUse,
                code = discount.code.value,
            )
        }
    }
}