package com.diva.productapp.utils

import com.diva.productapp.data.remote.response.ProductsItem
import com.diva.productapp.domain.model.Product

object DataMapper {
    fun ProductsItem.toProduct(): Product {
        return Product(
            id = id ?: 0,
            title = title.orEmpty(),
            description = description.orEmpty(),
            category = category.orEmpty(),
            price = price ?: 0.0,
            discountPercentage = discountPercentage ?: 0.0,
            rating = rating ?: 0.0,
            stock = stock ?: 0,
            brand = brand.orEmpty(),
            thumbnail = thumbnail.orEmpty(),
            images = images
                ?.filterNotNull()
                ?: emptyList()
        )
    }

    fun List<ProductsItem?>?.toProducts(): List<Product> {
        return this?.filterNotNull()?.map { it.toProduct() } ?: emptyList()
    }
}