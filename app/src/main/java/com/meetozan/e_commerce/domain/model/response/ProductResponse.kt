package com.meetozan.e_commerce.domain.model.response

import com.google.gson.annotations.SerializedName
import com.meetozan.e_commerce.data.dto.ProductDto

data class ProductResponse(
    @SerializedName("success")
    val success: Int = 0,
    @SerializedName("products")
    val productDtoResponse: List<ProductDto>?
)