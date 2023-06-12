package com.meetozan.e_commerce.ui.household

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
class HouseholdViewModel @Inject constructor(
    private val ioDispatcher: CoroutineContext,
    private val productRepository: ProductRepository
) : ViewModel() {

    private var _householdList = MutableLiveData<List<ProductDto>>()
    val householdList: LiveData<List<ProductDto>>
        get() = _householdList

    init {
        getHouseholdProducts()
    }

    private fun getHouseholdProducts() {
        CoroutineScope(ioDispatcher).launch {
            productRepository.getHousehold(_householdList)
        }
    }
}