package com.sinaglife.shoping_cart.read_model.domain

interface ProductApiClient {
    fun getProductDetails(productId: String): ProductDto
}