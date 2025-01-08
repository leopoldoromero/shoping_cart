package com.sinaglife.shoping_cart.cart.application.add_discount

import com.sinaglife.shoping_cart.shared.domain.bus.command.Command

data class AddDiscountToCartCommand(
    val id: String,
    val code: String,
) : Command
