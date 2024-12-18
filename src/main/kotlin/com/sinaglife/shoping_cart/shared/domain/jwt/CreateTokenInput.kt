package com.sinaglife.shoping_cart.shared.domain.jwt

import java.time.Instant
import java.time.temporal.ChronoUnit

data class TokenClaim<T>(val name: String, val value: T)

data class CreateTokenInput<T>(
    val issuedAt: Instant? = Instant.now(),
    val expiresAt: Instant = Instant.now().plus(1, ChronoUnit.DAYS),
    val claim: TokenClaim<T>,
    val subject: String?,
) {}

