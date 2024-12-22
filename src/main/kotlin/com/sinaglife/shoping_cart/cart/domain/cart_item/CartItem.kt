package com.sinaglife.shoping_cart.cart.domain.cart_item

import com.sinaglife.shoping_cart.shared.domain.Guard

data class CartItemPrimitives(
    val id: String,
    val name: String,
    val quantity: Int,
    val price: Int,
    val code: String,
    val stock: Int,
    val image: CartItemImgPrimitives
)

class CartItem private constructor(
    val id: CartItemId,
    val name: CartItemName,
    val quantity: CartItemQty,
    val price: CartItemPrice,
    val code: CartItemCode,
    val stock: CartItemStock,
    val image: CartItemImg
){
    companion object {
        fun create(
            id: CartItemId?,
            name: CartItemName,
            quantity: CartItemQty,
            price: CartItemPrice,
            code: CartItemCode,
            stock: CartItemStock,
            image: CartItemImg
        ): CartItem {
            Guard.validateAgainstNullOrUndefined(
                mapOf(
                    "name" to name,
                    "quantity" to quantity,
                    "price" to price,
                    "code" to code,
                    "stock" to stock,
                    "image" to image
                )
            )
            return CartItem(
                id = id ?: CartItemId.random(),
                name,
                quantity,
                price,
                code,
                stock,
                image
            )
        }

        fun fromPrimitives(
            props: CartItemPrimitives,
        ): CartItem {
            return CartItem(
                CartItemId.fromString(props.id),
                CartItemName(props.name),
                CartItemQty(props.quantity),
                CartItemPrice(props.price),
                CartItemCode(props.code),
                CartItemStock(props.stock),
                CartItemImg.fromPrimitives(props.image)
            )
        }
    }

    fun toPrimitives(): CartItemPrimitives {
        return CartItemPrimitives(
            id.value.toString(),
            name.value,
            quantity.value,
            price.value,
            code.value,
            stock.value,
            image.toPrimitives(),
        )
    }
}