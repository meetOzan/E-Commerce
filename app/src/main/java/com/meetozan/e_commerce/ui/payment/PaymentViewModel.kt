package com.meetozan.e_commerce.ui.payment

import androidx.lifecycle.ViewModel
import com.meetozan.e_commerce.data.repository.ProductRepository
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

    fun reduceStock(product_id : Int, product_stock: Int){
        CoroutineScope(ioDispatcher).launch {
            productRepository.updateStock(product_id, product_stock)
        }
    }

    fun deleteAllBasket(){
        CoroutineScope(ioDispatcher).launch {
            productRepository.deleteAllBasket()
        }
    }

}