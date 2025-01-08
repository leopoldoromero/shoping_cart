package com.sinaglife.shoping_cart.cart.application.add_discount

import com.sinaglife.shoping_cart.shared.domain.bus.command.CommandHandler
import org.springframework.stereotype.Service

@Service
class AddDiscountToCartCommandHandler() : CommandHandler<AddDiscountToCartCommand> {
    override fun handle(command: AddDiscountToCartCommand) {
        TODO("Not yet implemented")
    }
}