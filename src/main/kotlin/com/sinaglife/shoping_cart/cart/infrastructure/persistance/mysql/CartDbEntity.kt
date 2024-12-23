package com.sinaglife.shoping_cart.cart.infrastructure.persistance.mysql

import com.sinaglife.shoping_cart.cart.domain.Cart
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
) {
    companion object {
        fun fromDomainEntity(cart: Cart): CartDbEntity {
            val items: List<CartItemDbEntity> = cart.items.let { entityItems ->
                if (entityItems.isNotEmpty()) {
                    entityItems.map { item -> CartItemDbEntity.fromDomainEntity(item)
                    }
                } else {
                    emptyList()
                }
            }
            val discount = cart.discount?.let { CartDiscountDbEntity.fromDomainEntity(it) }
            return CartDbEntity(
                id = cart.id.value.toString(),
                discount = discount,
                items = items,
                createdAt = cart.createdAt,
                updatedAt = cart.updatedAt,
                customerId = cart.customerId.toString(),
            )
        }
    }
}