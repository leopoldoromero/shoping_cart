package com.sinaglife.shoping_cart

import com.sinaglife.shoping_cart.shared.config.EnvLoader
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ShoppingCartApplication

fun main(args: Array<String>) {
	EnvLoader.loadEnv()
	runApplication<ShoppingCartApplication>(*args)
}
