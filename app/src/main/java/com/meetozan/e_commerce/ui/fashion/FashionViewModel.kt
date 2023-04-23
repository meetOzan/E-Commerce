package com.meetozan.e_commerce.ui.fashion

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
class FashionViewModel @Inject constructor(
    private val productRepository: ProductRepository,
    private val ioDispatcher: CoroutineContext
) : ViewModel() {

    private val _fashionList = MutableLiveData<List<Product>>()
    val fashionList: MutableLiveData<List<Product>>
        get() = _fashionList

    init {
        getFashionProducts()
    }

    private fun getFashionProducts() {
        CoroutineScope(ioDispatcher).launch {
            productRepository.getFashion(_fashionList)
        }
    }
}