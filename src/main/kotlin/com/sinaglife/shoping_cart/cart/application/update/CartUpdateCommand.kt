package com.sinaglife.shoping_cart.cart.application.update

import com.sinaglife.shoping_cart.cart.domain.CartUpdateAction
import com.sinaglife.shoping_cart.cart.domain.cart_item.CartItemPrimitives
import com.sinaglife.shoping_cart.shared.domain.bus.command.Command

data class CartUpdateCommand(
    val id: String,
    val item: CartItemPrimitives,
    val action: CartUpdateAction
) : Command {
}