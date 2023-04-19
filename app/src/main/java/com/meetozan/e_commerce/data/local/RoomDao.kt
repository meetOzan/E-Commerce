package com.meetozan.e_commerce.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.meetozan.e_commerce.data.model.Product

@Dao
interface RoomDao {

    @Insert
    suspend fun addToFavorites(product: Product)

    @Delete
    suspend fun deleteFavorite(product: Product)

    @Query("SELECT * FROM product WHERE is_favorite = 'true'")
    suspend fun getFavorites() : List<Product>

}