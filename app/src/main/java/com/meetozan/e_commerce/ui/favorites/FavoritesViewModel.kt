package com.meetozan.e_commerce.ui.favorites

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.meetozan.e_commerce.data.dto.ProductDto
import com.meetozan.e_commerce.domain.repository.ProductRepository
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

    private val _favoritesList = MutableLiveData<List<ProductDto>>()
    val favoritesList: LiveData<List<ProductDto>>
        get() = _favoritesList

    private val _product = MutableLiveData<ProductDto>()
    val product: LiveData<ProductDto>
        get() = _product

    init {
        getFavorites()
    }

    private fun getFavorites() {
        CoroutineScope(ioDispatcher).launch {
            productRepository.getFavorites(_favoritesList, _product)
        }
    }

    fun addToFavorites(productDto: ProductDto, hashMap: HashMap<Any, Any>) {
        CoroutineScope(ioDispatcher).launch {
            productRepository.addToFavorites(productDto, hashMap)
        }
    }

    fun deleteFromFavorites(productDto: ProductDto) {
        CoroutineScope(ioDispatcher).launch {
            productRepository.deleteFromFavorites(productDto)
        }
    }

    fun addToBasket(productDto: ProductDto, hashMap: HashMap<Any, Any>){
        CoroutineScope(ioDispatcher).launch {
            productRepository.addToBasket(productDto,hashMap)
        }
    }

}