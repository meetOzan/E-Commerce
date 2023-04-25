package com.meetozan.e_commerce.ui.search

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
class SearchViewModel @Inject constructor(
    private val productRepository: ProductRepository,
    private val ioDispatcher: CoroutineContext
) : ViewModel() {

    private val _searchList = MutableLiveData<List<Product>>()
    val searchList: LiveData<List<Product>>
        get() = _searchList

    fun searchProduct(productName: String) {
        CoroutineScope(ioDispatcher).launch {
            productRepository.searchProducts(productName,_searchList)
        }
    }

}