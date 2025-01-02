package com.sinaglife.shoping_cart.read_model.infrastructure.controllers

import com.sinaglife.shoping_cart.read_model.application.search.SearchCartQuery
import com.sinaglife.shoping_cart.read_model.application.search.SearchCartQueryResponse
import com.sinaglife.shoping_cart.read_model.domain.CartItemReadModel
import com.sinaglife.shoping_cart.shared.domain.bus.query.QueryBus
import com.sinaglife.shoping_cart.shared.errors.CartDoesNotExistError
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDateTime

data class GetCartResponse(
    val id: String,
    val items: List<CartItemReadModel>,
    val subTotal: Int,
    val total: Int,
    val discount: Int?,
    val updatedAt: LocalDateTime,
    val customerId: String?,
)
data class GetCartErrorResponse(
    val error: String,
)

@RestController
class GetCartController(private val queryBus: QueryBus) {

    @GetMapping("/api/v1/carts/{id}")
    fun run(@PathVariable id: String): ResponseEntity<*> {
       return try {
           val cart = queryBus.ask(
               SearchCartQuery(id)
           ) as SearchCartQueryResponse
           return ResponseEntity.ok(GetCartResponse(
               id = cart.id,
               items = cart.items,
               subTotal = cart.subTotal,
               discount = cart.discount,
               total = cart.total,
               updatedAt = cart.updatedAt,
               customerId = cart.customerId,
           ))
       } catch (e: Exception) {
           when (e.cause) {
               is CartDoesNotExistError -> {
                   ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                       GetCartErrorResponse(
                           error = e.cause?.message ?: "Internal server error",
                       )
                   )
               } else -> {
                    ResponseEntity.badRequest().body(
                        GetCartErrorResponse(
                            error = e.message ?: "Internal server error",
                        )
                    )
                }
           }
       }
    }
}