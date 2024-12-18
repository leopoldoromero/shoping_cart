package com.sinaglife.shoping_cart.shared.domain

interface AppLogger {
    fun log(message: Any)
    fun error(message: Any)
    fun warn(message: Any)
    fun debug(message: Any)
}

