package com.sinaglife.shoping_cart.read_model.infrastructure

import com.sinaglife.shoping_cart.read_model.domain.ProductApiClient
import com.sinaglife.shoping_cart.read_model.domain.ProductDto
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono

@Service
class ProductApiClientAdapter(
    private val webClientBuilder: WebClient.Builder,
    @Value("\${product.api.base-url}") private val baseUrl: String
) : ProductApiClient {
    override fun getProductDetails(productId: String): ProductDto {
        return webClientBuilder.baseUrl(baseUrl)
            .build()
            .get()
            .uri("/api/v1/products/{id}", productId)
            .retrieve()
            .bodyToMono(ProductDto::class.java)
            .block()  // Blocking here to convert Mono<ProductDto> to ProductDto
            ?: throw RuntimeException("Product not found")
    }
}