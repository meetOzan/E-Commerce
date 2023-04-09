package com.meetozan.e_commerce.data

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Brand(

    @SerializedName("id")
    @Expose
    val id: Int,

    @SerializedName("name")
    @Expose
    val name: String,

    @SerializedName("picUrl")
    @Expose
    val picUrl: String,

) {
}