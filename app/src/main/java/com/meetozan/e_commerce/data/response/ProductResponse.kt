package com.meetozan.e_commerce.data.response

import com.google.gson.annotations.SerializedName
import com.meetozan.e_commerce.data.model.Product

data class ProductResponse(
    @SerializedName("success")
    val success: Int = 0,
    @SerializedName("products")
    val productResponse: List<Product>?
)