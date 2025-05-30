package com.dave.commercemainapp.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ProductResponse(
    @field:Json(name = "data")
    val products: List<Product>
)

@JsonClass(generateAdapter = true)
data class Product(
    @field:Json(name = "id")
    val id: Long,
    @field:Json(name = "name")
    val name: String,
    @field:Json(name = "image")
    val image: String,
    @field:Json(name = "originalPrice")
    val originalPrice: Long,
    @field:Json(name = "discountedPrice")
    val discountedPrice: Long = -1,
    @field:Json(name = "isSoldOut")
    val isSoldOut: Boolean,
    var favorite: Boolean = false
) {
    fun getDiscountPercent(): Int {
        return ((originalPrice - discountedPrice) / originalPrice.toDouble() * 100).toInt()
    }

    fun setProductFavorite(isFavorite: Boolean) {
        favorite = isFavorite
    }

}
