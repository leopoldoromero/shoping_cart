package com.sinaglife.shoping_cart.shared.domain.critreria

enum class CriteriaOrderType {
    ASC,
    DESC,
    NONE,
}
data class CriteriaOrder(val orderBy: String, val orderType: CriteriaOrderType) {
}