package com.sinaglife.shoping_cart.cart.domain.commands

import com.sinaglife.shoping_cart.cart.domain.cart_item.CartItemPrimitives
import com.sinaglife.shoping_cart.shared.domain.bus.command.Command

class CreateCartCommand(
    val id: String,
    val items: List<CartItemPrimitives>,
    val customerId: String?,
) : Command {
}