package com.sinaglife.shoping_cart.read_model.application.search

import com.sinaglife.shoping_cart.read_model.domain.CartReadModel
import com.sinaglife.shoping_cart.read_model.domain.CartReadRepository
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service

@Service
class CartSearcher(
    @Qualifier("CartReadModelMongoRepository")
    private val readRepository: CartReadRepository
) {
    fun execute(id: String): CartReadModel? {
        return readRepository.find(id)
    }
}