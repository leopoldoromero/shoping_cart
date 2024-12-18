package com.sinaglife.shoping_cart.shared.domain.bus.command

interface CommandBus {
    fun dispatch(command: Command)
}