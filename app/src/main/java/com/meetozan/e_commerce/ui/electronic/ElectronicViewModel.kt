package com.meetozan.e_commerce.ui.electronic

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
class ElectronicViewModel @Inject constructor(
    private val productRepository: ProductRepository,
    private val ioDispatcher: CoroutineContext
) : ViewModel() {

    private val _electronicList = MutableLiveData<List<ProductDto>>()
    val electronicList: LiveData<List<ProductDto>>
        get() = _electronicList

    init {
        getElectronicProducts()
    }

    private fun getElectronicProducts() {
        CoroutineScope(ioDispatcher).launch {
            productRepository.getElectronic(_electronicList)
        }
    }
}