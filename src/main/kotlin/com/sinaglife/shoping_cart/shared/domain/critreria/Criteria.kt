package com.sinaglife.shoping_cart.shared.domain.critreria

data class Criteria(
    val filters: List<CriteriaFilter> = emptyList(),
    val order: List<CriteriaOrder> = emptyList(),
    val page: Int? = null,
    val limit: Int? = null,
    val relations: List<String>? = emptyList(),
)