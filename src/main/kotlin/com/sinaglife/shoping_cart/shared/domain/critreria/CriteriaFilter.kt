package com.sinaglife.shoping_cart.shared.domain.critreria

sealed class ParamValue {
    data class SingleValue<T>(val value: T) : ParamValue()
    data class ArrayValue<T>(val values: List<T>) : ParamValue()
}

data class CriteriaFilter(
    val field: String,
    val value: Any,
    val operator: CriteriaOperators = CriteriaOperators.EQUAL,
    val alias: String? = "",
    val orFilters: List<CriteriaFilter>? = emptyList(),
) {}