package com.meetozan.e_commerce.data.model.model

data class Address(
    val name: String = "",
    val city: String = "",
    val district: String = "",
    val neighbourhood : String = "",
    val street: String = "",
    val apartment: String = "",
    val no: String = "",
    val fullAddress: String = "",
    val isDefault: Boolean = false
)