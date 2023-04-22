package com.meetozan.e_commerce.data.model.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class Product(

    @SerializedName("id")
    @ColumnInfo(name = "id")
    @Expose
    @PrimaryKey
    val id: Int = 0,

    @SerializedName("name")
    @ColumnInfo(name = "name")
    @Expose
    val name: String = "",

    @SerializedName("price")
    @ColumnInfo(name = "price")
    @Expose
    val price: Int = 0,

    @SerializedName("brand")
    @ColumnInfo(name = "brand")
    @Expose
    val brand: String = "",

    @SerializedName("picUrl")
    @ColumnInfo(name = "picUrl")
    @Expose
    val picUrl: String = "",

    @SerializedName("secondPicUrl")
    @ColumnInfo(name = "secondPicUrl")
    @Expose
    val secondPicUrl: String = "",

    @SerializedName("thirdPicUrl")
    @ColumnInfo(name = "thirdPicUrl")
    @Expose
    val thirdPicUrl: String = "",

    @SerializedName("description")
    @ColumnInfo(name = "description")
    @Expose
    val description: String = "",

    @ColumnInfo("is_favorite")
    var isFavorite: Boolean? = false,

    @SerializedName("rate")
    @ColumnInfo(name = "rate")
    @Expose
    var rate: Int = 0,

    @SerializedName("stock")
    @ColumnInfo(name = "stock")
    @Expose
    var stock: Int = 0

) : Parcelable