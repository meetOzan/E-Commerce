package com.meetozan.e_commerce.ui.favorites

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.meetozan.e_commerce.data.model.model.Product
import com.meetozan.e_commerce.data.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val productRepository: ProductRepository,
    private val ioDispatcher: CoroutineContext,
) : ViewModel() {

    private val _favoritesList = MutableLiveData<List<Product>>()
    val favoritesList: LiveData<List<Product>>
        get() = _favoritesList

    init {
        getFavorites()
    }

    private fun getFavorites() {
        CoroutineScope(ioDispatcher).launch {
            productRepository.getFavorites().let {
                _favoritesList.postValue(it)
            }
        }
    }

    fun updateProduct(product: Product) {
        CoroutineScope(ioDispatcher).launch {
            productRepository.updateProduct(product)
        }
    }

    fun addToFavorites(product: Product) {
        CoroutineScope(ioDispatcher).launch {
            productRepository.addToFavorites(product)
        }
    }

    fun deleteFromFavorites(product: Product) {
        CoroutineScope(ioDispatcher).launch {
            productRepository.deleteFromFavorites(product)
        }
    }
}