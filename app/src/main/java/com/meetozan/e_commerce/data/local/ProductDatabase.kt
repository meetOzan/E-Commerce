package com.meetozan.e_commerce.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.meetozan.e_commerce.data.model.Product

@Database(entities = [Product::class], version = 1)
abstract class ProductDatabase : RoomDatabase() {
    abstract fun productDao() : RoomDao

}