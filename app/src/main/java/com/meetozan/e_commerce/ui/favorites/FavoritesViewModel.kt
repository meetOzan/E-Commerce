package com.meetozan.e_commerce.ui.favorites

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
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

    private val _product = MutableLiveData<Product>()
    val product: LiveData<Product>
        get() = _product

    init {
        getFavorites()
    }

    private fun getFavorites() {
        CoroutineScope(ioDispatcher).launch {
            productRepository.getFavorites(_favoritesList, _product)
        }
    }

    fun addToFavorites(product: Product, hashMap: HashMap<Any, Any>) {
        CoroutineScope(ioDispatcher).launch {
            productRepository.addToFavorites(product, hashMap)
        }
    }

    fun deleteFromFavorites(product: Product) {
        CoroutineScope(ioDispatcher).launch {
            productRepository.deleteFromFavorites(product)
        }
    }

    fun addToBasket(product: Product,hashMap: HashMap<Any, Any>){
        CoroutineScope(ioDispatcher).launch {
            productRepository.addToBasket(product,hashMap)
        }
    }

}