package com.sinaglife.shoping_cart.cart.infrastructure.persistance.mysql

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "cart")
class CartDbEntity(
    @Id
    @Column(name = "id", nullable = false, updatable = false)
    val id: String,

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "cart", cascade = [CascadeType.ALL], orphanRemoval = true)
    val items: List<CartItemDbEntity>,

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "cart", cascade = [CascadeType.ALL], orphanRemoval = true)
    val discount: CartDiscountDbEntity? = null,

    @Column(name = "createdAt")
    val createdAt: LocalDateTime,

    @Column(name = "updatedAt")
    val updatedAt: LocalDateTime,

    @Column(name = "customerId")
    val customerId: String,
) {}