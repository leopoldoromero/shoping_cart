package com.sinaglife.shoping_cart.shared.domain

data class LogInput(
    val methodName: String,
    val input: Any? = null,
    val output: Any? = null,
    val message: String? = null
)