package com.meetozan.e_commerce.data.response

import com.google.gson.annotations.SerializedName
import com.meetozan.e_commerce.data.model.Brand

data class BrandResponse(
    @SerializedName("success")
    val success: Int = 0,
    @SerializedName("brands")
    val brandResponse: List<Brand>?
    )