package com.meetozan.e_commerce.domain.model.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val email: String = "",
    val username: String = "",
    val password: String = "",
    val number: String = "",
    val gender: String = "",
    val totalPrice: Int = 0
) : Parcelable