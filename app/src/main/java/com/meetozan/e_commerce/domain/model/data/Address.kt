package com.meetozan.e_commerce.domain.model.data

data class Address(
    val name: String = "",
    val city: String = "",
    val district: String = "",
    val neighbourhood : String = "",
    val street: String = "",
    val apartment: String = "",
    val no: String = "",
    val isDefault: Boolean? = false ,
)