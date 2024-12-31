package com.sinaglife.shoping_cart.cart.application.create

import com.sinaglife.shoping_cart.shared.domain.bus.command.CommandHandler
import org.springframework.stereotype.Service

@Service
class CreateCartCommandHandler(private val cartCreator: CartCreator) : CommandHandler<CreateCartCommand> {
    override fun handle(command: CreateCartCommand) {
        return cartCreator.execute(
            id = command.id,
            items = command.items,
            customerId = command.customerId,
        )
    }
}