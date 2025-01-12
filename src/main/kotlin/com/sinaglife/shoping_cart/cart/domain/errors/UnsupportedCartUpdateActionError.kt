package com.sinaglife.shoping_cart.cart.domain.errors

class UnsupportedCartUpdateActionError(action: String) : Exception("Operation ${action} not supported") {
}