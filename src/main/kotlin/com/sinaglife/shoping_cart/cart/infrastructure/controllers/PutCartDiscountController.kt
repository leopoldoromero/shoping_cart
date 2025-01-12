package com.sinaglife.shoping_cart.cart.infrastructure.controllers

import com.sinaglife.shoping_cart.cart.application.add_discount.AddDiscountToCartCommand
import com.sinaglife.shoping_cart.shared.domain.bus.command.CommandBus
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class PutCartDiscountController(private val commandBus: CommandBus) {

    @PutMapping("/api/v2/carts/discount")
    fun run(@RequestBody body: AddDiscountToCartRequest): ResponseEntity<CartWriteResponse> {
        return try {
            commandBus.dispatch(
                AddDiscountToCartCommand(cartId = body.cartId, code = body.code)
            )
            ResponseEntity.status(HttpStatus.OK).body(
                CartWriteResponse(
                    success = true,
                    message = "Operation success",
                )
            )
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                CartWriteResponse(
                    success = false,
                    message = e.cause?.message ?: e.message ?: "Unhandled error",
                )
            )
        }
    }
}