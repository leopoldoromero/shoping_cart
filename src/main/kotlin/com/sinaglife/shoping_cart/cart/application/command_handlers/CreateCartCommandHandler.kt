package com.sinaglife.shoping_cart.cart.application.command_handlers

import com.sinaglife.shoping_cart.cart.application.CartCreator
import com.sinaglife.shoping_cart.cart.domain.commands.CreateCartCommand
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