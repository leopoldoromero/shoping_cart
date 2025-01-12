package com.sinaglife.shoping_cart.cart.domain


sealed class CartExceptionsException(override val message: String, override val cause: Throwable? = null) : RuntimeException(message, cause)

data class InvalidIdException(val id: String, override val cause: Throwable?) : CartExceptionsException("The id <$id> is not a valid", cause)
