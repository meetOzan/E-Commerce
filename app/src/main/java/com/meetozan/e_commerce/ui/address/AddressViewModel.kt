package com.meetozan.e_commerce.ui.address

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.meetozan.e_commerce.data.model.model.Address
import com.meetozan.e_commerce.data.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

@HiltViewModel
class AddressViewModel @Inject constructor(
    private val ioDispatcher: CoroutineContext,
    private val productRepository: ProductRepository
) : ViewModel() {

    private val _addressList = MutableLiveData<List<Address>>()
    val addressList: LiveData<List<Address>>
        get() = _addressList


    init {
        getAllAddresses()
    }

    private fun getAllAddresses() {
        CoroutineScope(ioDispatcher).launch {
            productRepository.getAllAddress(_addressList)
        }
    }

    fun updateAddress(addressName: String, path: String, data: Any) {
        CoroutineScope(ioDispatcher).launch {
            productRepository.updateAddress(addressName, path, data)
        }
    }

    fun addAddress(address: HashMap<Any, Any>, addressName: String) {
        CoroutineScope(ioDispatcher).launch {
            productRepository.addAddress(address, addressName)
        }
    }

    fun deleteAddress(addressName: String) {
        CoroutineScope(ioDispatcher).launch {
            productRepository.deleteAddress(addressName)
        }
    }
}