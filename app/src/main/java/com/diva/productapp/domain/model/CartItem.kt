package com.diva.productapp.domain.model

data class CartItem(
    val product: Product,
    val quantity: Int = 0
) {
    val totalPrice: Double
        get() = product.price * quantity
}
