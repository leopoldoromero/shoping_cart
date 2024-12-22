package com.sinaglife.shoping_cart.cart.domain.cart_item

data class CartItemImgPrimitives(
    val name: String,
    val src: String,
    val alt: String
)

class CartItemImg(val name: String, val src: String, val alt: String) {
    companion object {
        fun fromPrimitives(props: CartItemImgPrimitives): CartItemImg {
            return CartItemImg(props.name, props.src, props.alt)
        }
    }

    fun toPrimitives(): CartItemImgPrimitives {
        return CartItemImgPrimitives(
            name,
            src,
            alt
        )
    }
}