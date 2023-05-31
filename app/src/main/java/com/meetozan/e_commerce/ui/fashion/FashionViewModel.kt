package com.meetozan.e_commerce.ui.fashion

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
class FashionViewModel @Inject constructor(
    private val productRepository: ProductRepository,
    private val ioDispatcher: CoroutineContext
) : ViewModel() {

    private val _fashionList = MutableLiveData<List<ProductDto>>()
    val fashionList: MutableLiveData<List<ProductDto>>
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