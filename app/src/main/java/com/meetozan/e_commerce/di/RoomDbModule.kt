package com.meetozan.e_commerce.di

import android.content.Context
import androidx.room.Room
import com.meetozan.e_commerce.data.local.ProductDatabase
import com.meetozan.e_commerce.data.local.RoomDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RoomDbModule {

    @Provides
    @Singleton
    fun provideProductDatabase(@ApplicationContext appContext: Context): ProductDatabase =
        Room.databaseBuilder(
            appContext,
            ProductDatabase::class.java,
            "product_local_database"
        )
            .fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .build()


    @Provides
    @Singleton
    fun provideProductDao(productDatabase: ProductDatabase): RoomDao =
        productDatabase.productDao()

}