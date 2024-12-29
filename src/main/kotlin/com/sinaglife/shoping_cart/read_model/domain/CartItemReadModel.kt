package com.sinaglife.shoping_cart.read_model.domain

data class CartItemReadModel(
    val id: String,
    val quantity: Int,
    val price: Int,
    val name: String,
    val stock: Int,
    val code: Int,
    val image: CartItemImgReadModel
)
