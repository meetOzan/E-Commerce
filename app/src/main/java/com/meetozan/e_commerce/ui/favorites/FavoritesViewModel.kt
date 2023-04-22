package com.meetozan.e_commerce.ui.favorites

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import com.meetozan.e_commerce.data.model.model.Product
import com.meetozan.e_commerce.data.model.model.User
import com.meetozan.e_commerce.data.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val productRepository: ProductRepository,
    private val ioDispatcher : CoroutineContext,
    private val firebaseFirestore: FirebaseFirestore,
    private val firebaseAuth: FirebaseAuth,
    @ApplicationContext val appContext : Context
) : ViewModel() {

    private val _favoritesList = MutableLiveData<List<Product>>()
    val favoritesList: LiveData<List<Product>>
        get() = _favoritesList

    private val _product = MutableLiveData<Product>()
    val product : LiveData<Product>
        get() = _product

    init {
        getFavorites()
    }

    private fun getFavorites() =
        firebaseFirestore.collection("users")
            .document(firebaseAuth.currentUser?.email.toString())
            .collection("favorites")
            .addSnapshotListener { querySnapshot, firestoreException ->
                firestoreException?.let {
                    Toast.makeText(appContext, it.message, Toast.LENGTH_LONG).show()
                    return@addSnapshotListener
                }
                querySnapshot?.let {
                    val favoritesList: ArrayList<Product> = ArrayList()
                    for (document in it) {
                        val favorites = document.toObject<Product>()
                        favoritesList.add(favorites)
                        _product.postValue(favorites)
                        _favoritesList.postValue(favoritesList)
                    }
                }
            }

    fun updateProduct(product: Product) {
        CoroutineScope(ioDispatcher).launch {
            productRepository.updateProduct(product)
        }
    }

    fun addToFavorites(product: Product,hashMap: HashMap<Any,Any>) {
        CoroutineScope(ioDispatcher).launch {
            productRepository.addToFavorites(product,hashMap)
        }
    }

    fun deleteFromFavorites(product: Product) {
        CoroutineScope(ioDispatcher).launch {
            productRepository.deleteFromFavorites(product)
        }
    }
}