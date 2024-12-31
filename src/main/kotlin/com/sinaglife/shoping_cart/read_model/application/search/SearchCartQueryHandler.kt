package com.sinaglife.shoping_cart.read_model.application.search

import com.sinaglife.shoping_cart.shared.errors.CartDoesNotExistError
import com.sinaglife.shoping_cart.shared.domain.bus.query.QueryHandler
import org.springframework.stereotype.Service

@Service
class SearchCartQueryHandler(
    private val cartSearcher: CartSearcher
) : QueryHandler<SearchCartQuery, SearchCartQueryResponse> {
    override fun handle(query: SearchCartQuery): SearchCartQueryResponse {
        val cartOrNull = cartSearcher.execute(query.id) ?: throw CartDoesNotExistError()
        return SearchCartQueryResponse(
            id = cartOrNull.id,
            items = cartOrNull.items,
            subTotal = cartOrNull.subTotal,
            discount = cartOrNull.discount,
            total = cartOrNull.total,
            customerId = cartOrNull.customerId,
            updatedAt = cartOrNull.updatedAt
        )
    }
}