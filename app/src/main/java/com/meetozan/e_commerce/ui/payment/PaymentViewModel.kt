package com.meetozan.e_commerce.ui.payment

import androidx.lifecycle.ViewModel
import com.meetozan.e_commerce.domain.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

@HiltViewModel
class PaymentViewModel @Inject constructor(
    private val ioDispatcher: CoroutineContext,
    private val productRepository: ProductRepository
) : ViewModel() {

    fun reduceStock(product_id: Int, product_stock: Int) {
        CoroutineScope(ioDispatcher).launch {
            productRepository.updateStock(product_id, product_stock)
        }
    }

    fun deleteFromBasket(product_name: String) {
        CoroutineScope(ioDispatcher).launch {
            productRepository.deleteFromBasket(product_name)
        }
    }

    fun addToOrders(product_name: String, hashMap: HashMap<Any, Any>) {
        CoroutineScope(ioDispatcher).launch {
            productRepository.addToOrders(product_name, hashMap)
        }
    }
}