package com.sinaglife.shoping_cart.cart.application.update

import com.sinaglife.shoping_cart.shared.domain.bus.command.CommandHandler
import org.springframework.stereotype.Service

@Service
class CartUpdateCommandHandler(private val cartUpdater: CartUpdater) : CommandHandler<CartUpdateCommand> {
    override fun handle(command: CartUpdateCommand) {
        return cartUpdater.execute(id = command.id, item = command.item, action = command.action)
    }
}