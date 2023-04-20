package com.meetozan.e_commerce.data.resources.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.meetozan.e_commerce.data.model.model.Product

@Database(entities = [Product::class], version = 3)
abstract class ProductDatabase : RoomDatabase() {
    abstract fun productDao() : RoomDao
}