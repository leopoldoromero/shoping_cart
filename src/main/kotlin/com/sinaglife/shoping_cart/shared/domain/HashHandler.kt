package com.sinaglife.shoping_cart.shared.domain

interface HashHandler {
    fun check(input: String, hash: String): Boolean
    fun hash(input: String): String
}