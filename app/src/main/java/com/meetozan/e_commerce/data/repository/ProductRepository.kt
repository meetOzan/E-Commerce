package com.meetozan.e_commerce.data.repository

import android.content.Context
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import com.meetozan.e_commerce.data.resources.local.RoomDao
import com.meetozan.e_commerce.data.model.model.Product
import com.meetozan.e_commerce.data.model.model.User
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class ProductRepository @Inject constructor(
    private val roomDao: RoomDao,
    private val firebaseAuth: FirebaseAuth,
    private val firebaseFirestore: FirebaseFirestore,
    @ApplicationContext val appContext : Context,
) {

    suspend fun addToFavorites(product: Product,hashMap: HashMap<Any,Any>) =
        firebaseFirestore.collection("users")
            .document(firebaseAuth.currentUser?.email.toString())
            .collection("favorites")
            .document(product.name)
            .set(hashMap).await()

    suspend fun updateProduct(product : Product) = roomDao.updateProduct(product)

    suspend fun deleteFromFavorites(product: Product) = firebaseFirestore.collection("users")
        .document(firebaseAuth.currentUser?.email.toString())
        .collection("favorites")
        .document(product.name)
        .delete().await()

}