package com.sinaglife.shoping_cart.read_model.domain

data class ProductImageDto(
    val alt: String,
    val id: Int,
    val name: String,
    val src: String,
)

data class ProductDto(
    val id: String,
    val categories: List<String>,
    val description: String,
    val images: List<ProductImageDto>,
    val name: String,
    val price: Int,
    val code: Int,
    val stock: Int
) {
}