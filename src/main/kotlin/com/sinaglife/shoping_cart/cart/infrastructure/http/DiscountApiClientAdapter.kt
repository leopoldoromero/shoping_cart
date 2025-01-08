package com.sinaglife.shoping_cart.cart.infrastructure.http

import com.sinaglife.shoping_cart.cart.domain.DiscountApiClient
import com.sinaglife.shoping_cart.cart.domain.cart_discount.CartDiscount
import com.sinaglife.shoping_cart.cart.domain.cart_discount.CartDiscountPrimitives
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient

@Service
class DiscountApiClientAdapter(
    private val webClientBuilder: WebClient.Builder,
    @Value("\${coupon.api.base-url}") private val baseUrl: String
) : DiscountApiClient {
    override fun getDiscount(code: String, subTotal: Int, customerId: String?): CartDiscount {
        val discount = webClientBuilder.baseUrl(baseUrl)
            .build()
            .get()
            .uri("/v2/coupons/{code}", code)
            .retrieve()
            .bodyToMono(CartDiscountPrimitives::class.java)
            .block()
            ?: throw Exception("Invalid coupon")
        return CartDiscount.fromPrimitives(discount)
    }
}
