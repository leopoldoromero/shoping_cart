package com.sinaglife.shoping_cart.read_model.infrastructure.controllers

import java.time.LocalDateTime

data class GetCartResponseItemImg(
    val name: String,
    val alt: String,
    val src: String,
)

data class GetCartResponseItem(
    val id: String,
    val quantity: Int,
    val price: Double,
    val name: String,
    val stock: Int,
    val code: Int,
    val image: GetCartResponseItemImg
)

data class GetCartResponse(
    val id: String,
    val items: List<GetCartResponseItem>,
    val subTotal: Double,
    val total: Double,
    val discount: Double?,
    val updatedAt: LocalDateTime,
    val customerId: String?,
) {
}