package com.meetozan.e_commerce.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.meetozan.e_commerce.data.dto.ProductDto
import com.meetozan.e_commerce.domain.model.data.User
import com.meetozan.e_commerce.domain.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val productRepository: ProductRepository,
    private val ioDispatcher: CoroutineContext
) : ViewModel() {

    private val _user = MutableLiveData<User>()
    val user: LiveData<User>
        get() = _user

    private val _orderList = MutableLiveData<List<ProductDto>>()
    val orderList : LiveData<List<ProductDto>>
        get() = _orderList

    init {
        getUser()
        getOrders()
    }

    private fun getUser() {
        CoroutineScope(ioDispatcher).launch {
            productRepository.getUser(_user).await()
        }
    }

    private fun getOrders(){
        CoroutineScope(ioDispatcher).launch {
            productRepository.getOrders(_orderList)
        }
    }

    fun signOut() {
        CoroutineScope(ioDispatcher).launch {
            productRepository.signOut()
        }
    }

    fun updateUser(hashMap: HashMap<String,Any>){
        CoroutineScope(ioDispatcher).launch {
            productRepository.updateUser(hashMap)
        }
    }
}