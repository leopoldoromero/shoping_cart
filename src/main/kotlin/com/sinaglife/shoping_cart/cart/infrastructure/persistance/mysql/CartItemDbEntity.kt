package com.sinaglife.shoping_cart.cart.infrastructure.persistance.mysql

import com.sinaglife.shoping_cart.cart.domain.cart_item.CartItem
import com.sinaglife.shoping_cart.cart.domain.cart_item.CartItemImgPrimitives
import jakarta.persistence.*

@Entity
@Table(name = "cart_item")
class CartItemDbEntity(
    @Id
    @Column(name = "id")
    val id: String,

    @Column(name = "name")
    val name: String,

    @Column(name = "quantity")
    val quantity: Int,

    @Column(name = "price")
    val price: Int,

    @Column(name = "stock")
    val stock: Int,

    @Column(name = "code")
    val code: String,

    @Column(name = "image", columnDefinition = "json")
    @Convert(converter = CartItemImgPrimitivesConverter::class)
    val image: CartItemImgPrimitives,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id")
    val cart: CartDbEntity? = null
) {
    companion object {
        fun fromDomainEntity(item: CartItem): CartItemDbEntity {
            return CartItemDbEntity(
                id = item.id.value.toString(),
                name = item.name.value,
                quantity = item.quantity.value,
                stock = item.stock.value,
                price = item.price.value,
                code = item.code.value,
                image = item.image.toPrimitives(),
            )
        }
    }
}
