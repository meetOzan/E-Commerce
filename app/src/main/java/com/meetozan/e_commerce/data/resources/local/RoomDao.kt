package com.meetozan.e_commerce.data.resources.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.meetozan.e_commerce.data.model.model.Product

@Dao
interface RoomDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addToFavorites(product: Product)

    @Delete
    suspend fun deleteFavorite(product: Product)

    @Update
    suspend fun updateProduct(product: Product)

    @Query("SELECT * FROM product WHERE is_favorite = 1")
    suspend fun getFavorites(): List<Product>

}