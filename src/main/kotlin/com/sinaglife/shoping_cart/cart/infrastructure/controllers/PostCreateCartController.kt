package com.sinaglife.shoping_cart.cart.infrastructure.controllers

import com.sinaglife.shoping_cart.cart.domain.cart_item.CartItemImgPrimitives
import com.sinaglife.shoping_cart.cart.domain.cart_item.CartItemPrimitives
import com.sinaglife.shoping_cart.cart.domain.commands.CreateCartCommand
import com.sinaglife.shoping_cart.shared.config.Constants
import com.sinaglife.shoping_cart.shared.domain.bus.command.CommandBus
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

data class CartItemImgRequest(
    val name: String,
    val src: String,
    val alt: String,
)
data class CartItemRequest(
    val id: String,
    val name: String,
    val quantity: Int,
    val price: Int,
    val stock: Int,
    val code: String,
    val image: CartItemImgRequest,
)
data class CreateCartRequest(
    val id: String,
    val items: List<CartItemRequest>,
    val customerId: String?
)
data class CreateCartResponse(val success: Boolean, val message: String)

@RestController
class PostCreateCartController(private val commandBus: CommandBus) {

    @PostMapping("/api/v1/carts")
    fun run(@RequestBody body: CreateCartRequest): ResponseEntity<CreateCartResponse> {
        return try {
            val items = body.items.map { item -> CartItemPrimitives(
                id = item.id,
                name = item.name,
                price = item.price,
                quantity = item.quantity,
                stock = item.stock,
                code = item.code,
                image = CartItemImgPrimitives(
                    name = item.image.name,
                    src = item.image.src,
                    alt = item.image.alt,
                ),
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
                    message = e.message ?: "Unhandled error",
                )
            )
        }
    }
}