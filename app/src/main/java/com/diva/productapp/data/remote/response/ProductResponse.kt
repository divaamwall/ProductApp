package com.diva.productapp.data.remote.response

import com.google.gson.annotations.SerializedName

data class ProductResponse(
    @field:SerializedName("products")
    val products: List<ProductsItem?>? = null,

    @field:SerializedName("total")
    val total: Int? = null,

    @field:SerializedName("skip")
    val skip: Int? = null,

    @field:SerializedName("limit")
    val limit: Int? = null
)

data class ProductsItem(

    @field:SerializedName("images")
    val images: List<String?>? = null,

    @field:SerializedName("thumbnail")
    val thumbnail: String? = null,

    @field:SerializedName("minimumOrderQuantity")
    val minimumOrderQuantity: Int? = null,

    @field:SerializedName("rating")
    val rating: Double? = null,

    @field:SerializedName("returnPolicy")
    val returnPolicy: String? = null,

    @field:SerializedName("description")
    val description: String? = null,

    @field:SerializedName("weight")
    val weight: Int? = null,

    @field:SerializedName("warrantyInformation")
    val warrantyInformation: String? = null,

    @field:SerializedName("title")
    val title: String? = null,

    @field:SerializedName("tags")
    val tags: List<String?>? = null,

    @field:SerializedName("discountPercentage")
    val discountPercentage: Double? = null,

    @field:SerializedName("reviews")
    val reviews: List<ReviewsItem?>? = null,

    @field:SerializedName("price")
    val price: Double? = null,

    @field:SerializedName("shippingInformation")
    val shippingInformation: String? = null,

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("availabilityStatus")
    val availabilityStatus: String? = null,

    @field:SerializedName("category")
    val category: String? = null,

    @field:SerializedName("stock")
    val stock: Int? = null,

    @field:SerializedName("sku")
    val sku: String? = null,

    @field:SerializedName("brand")
    val brand: String? = null,

    )

data class ReviewsItem(

    @field:SerializedName("date")
    val date: String? = null,

    @field:SerializedName("reviewerName")
    val reviewerName: String? = null,

    @field:SerializedName("reviewerEmail")
    val reviewerEmail: String? = null,

    @field:SerializedName("rating")
    val rating: Int? = null,

    @field:SerializedName("comment")
    val comment: String? = null
)
