package com.sinaglife.shoping_cart.cart.infrastructure.controllers

import com.sinaglife.shoping_cart.cart.domain.cart_item.CartItemPrimitives
import com.sinaglife.shoping_cart.cart.application.create.CreateCartCommand
import com.sinaglife.shoping_cart.shared.domain.bus.command.CommandBus
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.ExampleObject
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.parameters.RequestBody
import io.swagger.v3.oas.annotations.responses.ApiResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class PostCreateCartController(private val commandBus: CommandBus) {

    @Operation(
        summary = "Create a cart",
        description = "Create a cart by providing its id and items",
        requestBody = RequestBody(
            description = "Cart creation request body",
            required = true,
            content = [
                Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = CreateCartRequest::class),
                    examples = [
                        ExampleObject(
                            name = "Cart creation request body example",
                            value = """
                                {
                                    "id": "1bf12867-3cc7-4db9-b5be-abb1ba1c4c57",
                                    "items": [
                                        {
                                            "id": "a182124f-89f1-42ad-9c6f-e52f8c2b92e6",
                                            "price": 20.00,
                                            "quantity": 1
                                        }
                                    ]
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
                description = "Cart created successfully",
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
    @PostMapping("/api/v2/carts")
    fun run(@RequestBody body: CreateCartRequest): ResponseEntity<CartWriteResponse> {
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