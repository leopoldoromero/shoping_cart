package com.sinaglife.shoping_cart.shared.domain.security

@Target(AnnotationTarget.FUNCTION, AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
annotation class SecuredRoute(val role: String, val authStrategy: String = "JWT")