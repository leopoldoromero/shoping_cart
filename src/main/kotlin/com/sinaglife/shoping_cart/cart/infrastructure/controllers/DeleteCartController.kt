package com.sinaglife.shoping_cart.cart.infrastructure.controllers

import com.sinaglife.shoping_cart.cart.application.delete.DeleteCartCommand
import com.sinaglife.shoping_cart.shared.domain.bus.command.CommandBus
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

data class DeleteCartResponse(val success: Boolean, val message: String)

@RestController
class DeleteCartController(private val commandBus: CommandBus) {

    @DeleteMapping("/api/v2/carts/{id}")
    fun run(@PathVariable id: String): ResponseEntity<DeleteCartResponse> {
        return try {
            commandBus.dispatch(DeleteCartCommand(id))
            ResponseEntity.status(HttpStatus.OK).body(
                DeleteCartResponse(
                    success = true,
                    message = "Operation success",
                )
            )
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                DeleteCartResponse(
                    success = false,
                    message = e.cause?.message ?: e.message ?: "Unhandled error",
                )
            )
        }
    }
}