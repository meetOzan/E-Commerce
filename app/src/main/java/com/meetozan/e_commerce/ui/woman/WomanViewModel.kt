package com.meetozan.e_commerce.ui.woman

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
class WomanViewModel @Inject constructor(
    private val ioDispatcher: CoroutineContext,
    private val productRepository: ProductRepository
) : ViewModel() {

    private var _womanList = MutableLiveData<List<ProductDto>>()
    val womanList: LiveData<List<ProductDto>>
        get() = _womanList

    init {
        getWomanProducts()
    }

    private fun getWomanProducts() {
        CoroutineScope(ioDispatcher).launch {
            productRepository.getWoman(_womanList)
        }
    }
}