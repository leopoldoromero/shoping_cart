package com.sinaglife.shoping_cart.read_model.infrastructure.controllers

import com.sinaglife.shoping_cart.read_model.application.search.SearchCartQuery
import com.sinaglife.shoping_cart.read_model.application.search.SearchCartQueryResponse
import com.sinaglife.shoping_cart.shared.domain.bus.query.QueryBus
import com.sinaglife.shoping_cart.shared.errors.CartDoesNotExistError
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.ExampleObject
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

data class GetCartErrorResponse(
    val error: String,
)

@RestController
class GetCartController(private val queryBus: QueryBus) {

    @Operation(
        summary = "Retrieve a cart by its ID",
        description = "Fetch the details of a shopping cart by providing its unique identifier.",
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "Cart retrieved successfully",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = GetCartResponse::class),
                        examples = [
                            ExampleObject(
                                name = "Success Response",
                                value = """
                                    {
                                        "id": "1bf12867-3cc7-4db9-b5be-abb1ba1c4c57",
                                        "items": [
                                            {
                                                "id": "a182124f-89f1-42ad-9c6f-e52f8c2b92e6",
                                                "quantity": 1,
                                                "price": 20.00,
                                                "name": "Sample Item",
                                                "stock": 3,
                                                "code": 1234,
                                                "image": {
                                                    "name": "Sample Image",
                                                    "alt": "Image Alt Text",
                                                    "src": "https://example.com/image.png"
                                                }
                                            }
                                        ],
                                        "subTotal": 20.00,
                                        "discount": 0,
                                        "total": 20.00,
                                        "updatedAt": "2025-01-01T12:00:00",
                                        "customerId": null
                                    }
                                """
                            )
                        ]
                    )
                ]
            ),
            ApiResponse(
                responseCode = "400",
                description = "Invalid request, cart does not exist",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = GetCartErrorResponse::class),
                        examples = [
                            ExampleObject(
                                name = "Error Response",
                                value = """
                                    {
                                        "error": "Cart does not exist"
                                    }
                                """
                            )
                        ]
                    )
                ]
            )
        ]
    )
    @GetMapping("/api/v2/carts/{id}")
    fun run(@PathVariable id: String): ResponseEntity<*> {
       return try {
           val cart = queryBus.ask(
               SearchCartQuery(id)
           ) as SearchCartQueryResponse
           return ResponseEntity.ok(toDto(cart))
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

    private fun toDto(input: SearchCartQueryResponse): GetCartResponse {
        return GetCartResponse(
            id = input.id,
            items = input.items.map { it -> GetCartResponseItem(
                id = it.id,
                name = it.name,
                price = it.price,
                stock = it.stock,
                quantity = it.quantity,
                code = it.code,
                image = GetCartResponseItemImg(
                    alt = it.image.alt,
                    src = it.image.src,
                    name = it.image.name,
                )
            ) },
            subTotal = input.subTotal,
            discount = input.discount,
            total = input.total,
            updatedAt = input.updatedAt,
            customerId = input.customerId,
        )
    }
}