package com.sinaglife.shoping_cart.shared.domain

abstract class EnumValueObject<T>(
    val value: T,
    private val validValues: List<T>
) {
    init {
        checkValueIsValid(value)
    }

    private fun checkValueIsValid(value: T) {
        if (!validValues.contains(value)) {
            throwErrorForInvalidValue(value)
        }
    }

    protected abstract fun throwErrorForInvalidValue(value: T)
}
