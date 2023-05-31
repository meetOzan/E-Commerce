package com.meetozan.e_commerce.ui.newest

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
class NewestViewModel @Inject constructor(
    private val ioDispatcher: CoroutineContext,
    private val productRepository: ProductRepository
) : ViewModel() {

    private val _newestList = MutableLiveData<List<ProductDto>>()
    val newestList: LiveData<List<ProductDto>>
        get() = _newestList

    init {
        getNewest()
    }

    private fun getNewest() {
        CoroutineScope(ioDispatcher).launch {
            productRepository.getNewest(_newestList)
        }
    }
}