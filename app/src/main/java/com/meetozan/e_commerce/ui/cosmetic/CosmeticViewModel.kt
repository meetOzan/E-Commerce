package com.meetozan.e_commerce.ui.cosmetic

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
class CosmeticViewModel @Inject constructor(
    private val productRepository: ProductRepository,
    private val ioDispatcher: CoroutineContext
) : ViewModel() {

    private val _cosmeticList = MutableLiveData<List<Product>>()
    val cosmeticList: LiveData<List<Product>>
        get() = _cosmeticList

    init {
        getCosmeticProducts()
    }

    private fun getCosmeticProducts() {
        CoroutineScope(ioDispatcher).launch {
            productRepository.getCosmetic(_cosmeticList)
        }
    }
}