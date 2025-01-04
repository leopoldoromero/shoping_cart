package com.sinaglife.shoping_cart.cart.infrastructure.controllers

import com.sinaglife.shoping_cart.cart.application.update.CartUpdateCommand
import com.sinaglife.shoping_cart.cart.domain.cart_item.CartItemPrimitives
import com.sinaglife.shoping_cart.shared.domain.bus.command.CommandBus
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.ExampleObject
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class PutCartItemController(private val commandBus: CommandBus) {

    @Operation(
        summary = "Update a cart",
        description = "Update the cart by providing items to add or remove",
        requestBody = io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Cart update request body",
            required = true,
            content = [
                Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = UpdateCartRequest::class),
                    examples = [
                        ExampleObject(
                            name = "Cart update request body example",
                            value = """
                                {
                                    "id": "1bf12867-3cc7-4db9-b5be-abb1ba1c4c57",
                                    "item": {
                                            "id": "40676af1-09b2-49da-9665-8d9901ae9bf9",
                                            "price": 8.00,
                                            "quantity": 1
                                        },
                                    "action": "ADD"
                                }
                            """
                        )
                    ]
                )
            ]
        ),
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "Cart updated successfully",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = CartWriteResponse::class),
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
                        schema = Schema(implementation = CartWriteResponse::class),
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
    @PutMapping("/api/v2/carts/items")
    fun run(@RequestBody body: UpdateCartRequest): ResponseEntity<CartWriteResponse> {
        return try {
            commandBus.dispatch(
                CartUpdateCommand(
                    id = body.id,
                    item = CartItemPrimitives(
                        id = body.item.id,
                        price = body.item.price,
                        quantity = body.item.quantity,
                    ),
                    action = body.action,
                )
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