package com.sinaglife.shoping_cart.cart.infrastructure.controllers

import com.sinaglife.shoping_cart.cart.application.update.CartUpdateCommand
import com.sinaglife.shoping_cart.cart.domain.CartUpdateAction
import com.sinaglife.shoping_cart.cart.domain.cart_item.CartItemPrimitives
import com.sinaglife.shoping_cart.shared.domain.bus.command.CommandBus
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

data class UpdateCartItemRequest(
    val id: String,
    val item: CartItemPrimitives,
    val action: CartUpdateAction
)
data class UpdateCartItemResponse(val success: Boolean, val message: String)

@RestController
class PutCartItemController(private val commandBus: CommandBus) {

    @PutMapping("/api/v1/carts/items")
    fun run(@RequestBody body: UpdateCartItemRequest): ResponseEntity<UpdateCartItemResponse> {
        return try {
            commandBus.dispatch(
                CartUpdateCommand(
                    id = body.id,
                    item = body.item,
                    action = body.action,
                )
            )
            ResponseEntity.status(HttpStatus.CREATED).body(
                UpdateCartItemResponse(
                    success = true,
                    message = "Operation success",
                )
            )
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                UpdateCartItemResponse(
                    success = false,
                    message = e.message ?: "Unhandled error",
                )
            )
        }
    }
}