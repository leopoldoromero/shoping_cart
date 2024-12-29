package com.sinaglife.shoping_cart.read_model.domain

import reactor.core.publisher.Mono

interface ProductApiClient {
    fun getProductDetails(productId: String): ProductDto
}