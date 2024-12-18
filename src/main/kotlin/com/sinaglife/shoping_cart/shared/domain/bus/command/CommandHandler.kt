package com.sinaglife.shoping_cart.shared.domain.bus.command

interface CommandHandler<T : Command> {
   fun handle(command: T)
}