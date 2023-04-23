package com.meetozan.e_commerce.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.meetozan.e_commerce.data.model.model.Brand
import com.meetozan.e_commerce.data.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val productRepository: ProductRepository,
    private val ioDispatcher: CoroutineContext
) : ViewModel() {

    private var _brandList = MutableLiveData<List<Brand>>()
    val brandList: LiveData<List<Brand>>
        get() = _brandList

    init {
        getBrands()
    }

    private fun getBrands() {
        CoroutineScope(ioDispatcher).launch {
            productRepository.getBrands(_brandList)
        }
    }
}