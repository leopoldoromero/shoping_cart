package com.sinaglife.shoping_cart.read_model.infrastructure.controllers

import com.sinaglife.shoping_cart.read_model.application.search.SearchCartQuery
import com.sinaglife.shoping_cart.shared.domain.bus.query.QueryBus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class GetCartController(private val queryBus: QueryBus) {

    @GetMapping("/api/v1/carts/{id}")
    fun run(@PathVariable id: String): ResponseEntity<*> {
        val cart = queryBus.ask(
            SearchCartQuery(id)
        )
        return ResponseEntity.ok(cart)
    }
}