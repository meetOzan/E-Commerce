package com.meetozan.e_commerce.data.repository

import com.meetozan.e_commerce.data.local.RoomDao
import com.meetozan.e_commerce.data.model.Product
import javax.inject.Inject

class ProductRepository @Inject constructor(
    private val roomDao: RoomDao
) {

    suspend fun addToFavorites(product: Product) = roomDao.addToFavorites(product)

    suspend fun getFavorites() = roomDao.getFavorites()

    suspend fun deleteFromFavorites(product: Product) = roomDao.deleteFavorite(product)

}