package com.sinaglife.shoping_cart.shared.domain

interface AppLogger {
    fun log(input: LogInput)
    fun error(input: LogInput)
    fun warn(input: LogInput)
    fun debug(input: LogInput)
}

