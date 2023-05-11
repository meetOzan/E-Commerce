package com.meetozan.e_commerce.ui.shopping_cart

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
class ShoppingCartViewModel @Inject constructor(
    private val productRepository: ProductRepository,
    private val ioDispatcher: CoroutineContext
) : ViewModel() {

    private val _basketList = MutableLiveData<List<Product>>()
    val basketList: MutableLiveData<List<Product>>
        get() = _basketList

    init {
        getBasket()
    }

    private fun getBasket() {
        CoroutineScope(ioDispatcher).launch {
            productRepository.getBasket(_basketList)
        }
    }

    fun updateBasketItem(data: Any, product: Product, path: String) {
        CoroutineScope(ioDispatcher).launch {
            productRepository.updateBasketItem(product, data, path)
        }
    }

    fun deleteProduct(product: Product) {
        CoroutineScope(ioDispatcher).launch {
            productRepository.deleteFromBasket(product)
        }
    }

}