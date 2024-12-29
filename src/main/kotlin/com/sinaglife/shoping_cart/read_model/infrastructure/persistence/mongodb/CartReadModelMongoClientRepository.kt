package com.sinaglife.shoping_cart.read_model.infrastructure.persistence.mongodb

import org.springframework.data.mongodb.repository.MongoRepository

interface CartReadModelMongoClientRepository : MongoRepository<CartReadModelDbDocument, String?> {
}