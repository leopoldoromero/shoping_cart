package com.sinaglife.shoping_cart.cart.application.delete

import com.sinaglife.shoping_cart.shared.domain.bus.command.Command

data class DeleteCartCommand(val id: String) : Command {
}