package com.sinaglife.shoping_cart.cart.infrastructure.persistance.mongodb

import org.springframework.data.mongodb.repository.MongoRepository

interface CartMongoClientRepository : MongoRepository<CartDbSchema, String?> {
}