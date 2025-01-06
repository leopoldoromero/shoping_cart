package com.sinaglife.shoping_cart.cart.infrastructure.controllers

import com.sinaglife.shoping_cart.cart.application.delete.DeleteCartCommand
import com.sinaglife.shoping_cart.read_model.infrastructure.controllers.GetCartErrorResponse
import com.sinaglife.shoping_cart.read_model.infrastructure.controllers.GetCartResponse
import com.sinaglife.shoping_cart.shared.domain.bus.command.CommandBus
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.ExampleObject
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class DeleteCartController(private val commandBus: CommandBus) {

    @Operation(
        summary = "Delete a cart by its ID",
        description = "Delete the shopping cart by providing its unique identifier.",
        parameters = [
            Parameter(
                name = "id",
                description = "The unique identifier of the shopping cart.",
                required = true,
                example = "1bf12867-3cc7-4db9-b5be-abb1ba1c4c57"
            )
        ],
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "Cart deleted successfully",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = GetCartResponse::class),
                        examples = [
                            ExampleObject(
                                name = "Success Response",
                                value = """
                                    {
                                        "success": true,
                                        "message": "Operation success"
                                    }
                                """
                            )
                        ]
                    )
                ]
            ),
            ApiResponse(
                responseCode = "500",
                description = "Invalid request",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = GetCartErrorResponse::class),
                        examples = [
                            ExampleObject(
                                name = "Error Response",
                                value = """
                                    {
                                        "success": false,
                                        "message": "Unhandled Error"
                                    }
                                """
                            )
                        ]
                    )
                ]
            )
        ]
    )
    @DeleteMapping("/api/v2/carts/{id}")
    fun run(@PathVariable id: String): ResponseEntity<CartWriteResponse> {
        return try {
            commandBus.dispatch(DeleteCartCommand(id))
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