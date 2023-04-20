package com.meetozan.e_commerce.data.model

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
    @Expose
    @PrimaryKey
    val id: Int,

    @SerializedName("name")
    @Expose
    val name: String,

    @SerializedName("price")
    @Expose
    val price: Int,

    @SerializedName("brand")
    @Expose
    val brand: String,

    @SerializedName("picUrl")
    @Expose
    val picUrl: String,

    @SerializedName("secondPicUrl")
    @Expose
    val secondPicUrl: String,

    @SerializedName("thirdPicUrl")
    @Expose
    val thirdPicUrl : String,

    @SerializedName("description")
    @Expose
    val description : String,

    @ColumnInfo("is_favorite")
    var isFavorite : Boolean? = false,

    @SerializedName("rate")
    @Expose
    var rate : Int,

    @SerializedName("stock")
    @Expose
    var stock : Int

) : Parcelable