package com.meetozan.e_commerce.ui.man

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
class ManViewModel @Inject constructor(
    private val ioDispatcher: CoroutineContext,
    private val productRepository: ProductRepository
) : ViewModel() {

    private val _manList = MutableLiveData<List<Product>>()
    val manList: LiveData<List<Product>>
        get() = _manList

    init {
        getManProduct()
    }

    private fun getManProduct() {
        CoroutineScope(ioDispatcher).launch {
            productRepository.getMan(_manList)
        }
    }
}