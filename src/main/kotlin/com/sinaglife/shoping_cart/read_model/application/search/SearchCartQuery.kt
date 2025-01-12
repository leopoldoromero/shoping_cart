package com.sinaglife.shoping_cart.read_model.application.search

import com.sinaglife.shoping_cart.read_model.domain.CartItemReadModel
import com.sinaglife.shoping_cart.shared.domain.bus.query.Query
import com.sinaglife.shoping_cart.shared.domain.bus.query.QueryResponse
import java.time.LocalDateTime

data class SearchCartQueryResponse(
    val id: String,
    val items: List<CartItemReadModel>,
    val subTotal: Double,
    val total: Double,
    val discount: Double?,
    val updatedAt: LocalDateTime,
    val customerId: String?
) : QueryResponse

class SearchCartQuery(val id: String) : Query