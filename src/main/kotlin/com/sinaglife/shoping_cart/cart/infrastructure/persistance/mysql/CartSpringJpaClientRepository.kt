package com.sinaglife.shoping_cart.cart.infrastructure.persistance.mysql

import org.springframework.data.jpa.repository.JpaRepository

interface CartSpringJpaClientRepository : JpaRepository<CartDbEntity, Long?> {
}
