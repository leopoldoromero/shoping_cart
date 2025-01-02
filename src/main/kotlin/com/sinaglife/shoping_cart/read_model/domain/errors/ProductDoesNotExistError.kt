package com.sinaglife.shoping_cart.read_model.domain.errors

class ProductDoesNotExistError(id: String) : Exception("Product ${id} does not exist or its out of stock") {
}