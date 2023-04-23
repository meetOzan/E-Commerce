package com.meetozan.e_commerce.ui.household

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
class HouseholdViewModel @Inject constructor(
    private val ioDispatcher: CoroutineContext,
    private val productRepository: ProductRepository
) : ViewModel() {

    private val _householdList = MutableLiveData<List<Product>>()
    val householdList: LiveData<List<Product>>
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