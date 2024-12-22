package com.sinaglife.shoping_cart.cart.infrastructure.persistance.mysql

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
}