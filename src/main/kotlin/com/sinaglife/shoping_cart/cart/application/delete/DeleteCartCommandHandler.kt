package com.sinaglife.shoping_cart.cart.application.delete

import com.sinaglife.shoping_cart.shared.domain.bus.command.CommandHandler
import org.springframework.stereotype.Service

@Service
class DeleteCartCommandHandler(private val cartDeleter: CartDeleter) : CommandHandler<DeleteCartCommand> {
    override fun handle(command: DeleteCartCommand) {
        cartDeleter.execute(command.id)
    }
}