package com.meetozan.e_commerce.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.meetozan.e_commerce.data.model.model.Product
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class ProductRepository @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firebaseFirestore: FirebaseFirestore,
) {

    suspend fun addToFavorites(product: Product, hashMap: HashMap<Any, Any>) =
        firebaseFirestore.collection("users")
            .document(firebaseAuth.currentUser?.email.toString())
            .collection("favorites")
            .document(product.name)
            .set(hashMap).await()

    suspend fun deleteFromFavorites(product: Product) = firebaseFirestore.collection("users")
        .document(firebaseAuth.currentUser?.email.toString())
        .collection("favorites")
        .document(product.name)
        .delete().await()

}