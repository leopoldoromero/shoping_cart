package com.sinaglife.shoping_cart.cart.infrastructure.controllers

import com.sinaglife.shoping_cart.cart.domain.cart_item.CartItemPrimitives
import com.sinaglife.shoping_cart.cart.application.create.CreateCartCommand
import com.sinaglife.shoping_cart.shared.domain.bus.command.CommandBus
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

data class CartItemRequest(
    val id: String,
    val quantity: Int,
    val price: Int,
)
data class CreateCartRequest(
    val id: String,
    val items: List<CartItemRequest>,
    val customerId: String?
)
data class CreateCartResponse(val success: Boolean, val message: String)

@RestController
class PostCreateCartController(private val commandBus: CommandBus) {

    @PostMapping("/api/v2/carts")
    fun run(@RequestBody body: CreateCartRequest): ResponseEntity<CreateCartResponse> {
        return try {
            val items = body.items.map { item -> CartItemPrimitives(
                id = item.id,
                price = item.price,
                quantity = item.quantity,
            )}
            commandBus.dispatch(
                CreateCartCommand(
                    id = body.id,
                    items = items,
                    customerId = body.customerId,
                )
            )
            ResponseEntity.status(HttpStatus.CREATED).body(
                CreateCartResponse(
                    success = true,
                    message = "Operation success",
                )
            )
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                CreateCartResponse(
                    success = false,
                    message = e.cause?.message ?: e.message ?: "Unhandled error",
                )
            )
        }
    }
}